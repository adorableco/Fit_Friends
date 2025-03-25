const { GoogleGenerativeAI } = require("@google/generative-ai");

export const generateReviewByGemini = async (shas: string[]): Promise<string[]> => {
    const genAI = new GoogleGenerativeAI(geminiKey);
    const model = genAI.getGenerativeModel({ model: "gemini-2.0-flash" });
    var reviews = [];

    for (const sha of shas) {
        const blob = await octokit.rest.git.getBlob({
            owner,
            repo,
            file_sha: sha,
            });

        const prompt =
        `
            당신은 시니어 소프트웨어 엔지니어입니다. 다음 코드 변경사항을 리뷰하고 핵심적인 2-3가지 개선점을 제안해주세요.
            
            - 다음 내용을 중점적으로 봐주세요:
            1. 잠재적인 버그나 이슈
            2. 코드 스타일 개선점
            3. 성능 고려사항
            
            응답 형식:
            - 한국어로 작성해주세요
            - 이모지 사용이 가능합니다
            - 핵심적인 내용만 간단명료하게 설명해주세요
            - 실제 코드 예시가 있으면 더 좋습니다
            
            코드 변경사항:
            ${blob}
            
            마크다운 형식으로 응답해주세요.
        `
        const result = await model.generateContent(prompt);
        console.log(result.response.text());
        reviews.push(result.response.text());
    }

    
    return reviews;
}
