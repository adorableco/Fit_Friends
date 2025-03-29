const { GoogleGenerativeAI } = require("@google/generative-ai");

export const generateReviewByGemini = async (blobContents: string[]): Promise<string[]> => {
    const genAI = new GoogleGenerativeAI(process.env.GEMINI_KEY);
    const model = genAI.getGenerativeModel({ model: "gemini-2.0-flash" });
    var reviews = [];

    const contentsToProcess = blobContents.slice(0, 3);
    for (const content of contentsToProcess) {
        const prompt =
        `
            You are a senior developer. Please review the following code and provide your feedback in Korean.
            Use Markdown formatting.
            Be concise and to the point.
            Use emojis if helpful.
            Include code examples if possible.

            Here is the code:
            ${content}
        `
        const result = await model.generateContent(prompt);
        console.log(result.response.text());
        reviews.push(result.response.text());
    }

    return reviews;
}
