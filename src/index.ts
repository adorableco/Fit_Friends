
import * as core from "@actions/core";
import { addLabels } from "./api";
import { initialize } from "./initialize";
import {generateReviewByGemini} from "./gemini/GeminiClient";

const updateLabel = async (number: number): Promise<boolean> => {
    return addLabels(number)
        .then(() => {
            core.info(`PR #${number}에 라벨을 성공적으로 추가했습니다.`);
            return true;
        })
        .catch(error => {
            core.warning(`PR #${number}에 라벨 추가에 실패했습니다: ${error.message}`);
            throw error;
  });
};


async function run() {
  try {
    initialize();
    
    const { data: pulls } = await octokit.rest.pulls.list({
      owner: global.owner,
      repo: global.repo,
      state: 'open'
    });

    const now = new Date();
    for (const pull of pulls) {
      const createdAt = new Date(pull.created_at);
      const diffInHours = (now.getTime() - createdAt.getTime()) / (1000 * 60 * 60);
      
      if (diffInHours >= 1) {
        const { data: reviews } = await octokit.rest.pulls.listReviews({
          owner,
          repo,
          pull_number: pull.number
        });
        
        if (reviews.length === 0) {
          core.info(`PR #${pull.number}에 리뷰가 없습니다.`);
          
          const changedFiles = await octokit.rest.pulls.listFiles({
              owner,
              repo,
              pull_number: pull.number,
            });

          const shas = changedFiles.data.map(file => file.sha);

          const reviews = await generateReviewByGemini(shas);

        for (const review of reviews) {
            await octokit.rest.issues.createComment({
              owner,
              repo,
              issue_number: pull.number,
              body: review
            });
          
        }

        core.info(`PR #${pull.number}에 리뷰를 남겼습니다.`);

        Promise.all([
              updateLabel(pull.number)
            ]);
        }
      }else{
        core.info(`PR #${pull.number}는 1시간이 지나지 않았습니다. 현재 경과 시간 : ${Math.round(diffInHours / 0.0167)}분`);
      }
    }
  } catch (error: any) {
    core.setFailed(error.message);
  }
}

run();

