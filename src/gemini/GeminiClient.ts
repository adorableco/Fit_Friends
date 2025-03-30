const { GoogleGenerativeAI } = require("@google/generative-ai");

interface FileContent {
    fileName: string;
    content: string;
}

export const generateReviewByGemini = async (blobContents: FileContent[]): Promise<string[]> => {
    const genAI = new GoogleGenerativeAI(process.env.GEMINI_KEY);
    const model = genAI.getGenerativeModel({ model: "gemini-2.0-flash" });
    var reviews = [];

    for (const file of blobContents) {
        if(file.content.length > 1000000) {
            const result = `${file.fileName}: 코드 길이가 너무 길어 AI가 리뷰를 작성할 수 없습니다.`;
            reviews.push(result);
            continue;
        }
        
        const prompt =
        `
            You are a senior developer. Please review the following code and provide your feedback in Korean.
            Use Markdown formatting.
            Be concise and to the point.
            Use emojis if helpful.
            Include code examples if possible.
            The response should start with '## 💭 Code Review: {File Name} '

            File name: ${file.fileName}
            Here is the code:
            ${file.content}
        `
        const result = await model.generateContent(prompt);
        console.log(result.response.text());
        reviews.push(result.response.text());
    }

    return reviews;
}
