import * as core from "@actions/core";
import { addLabels } from "./api";
import { initialize } from "./initialize";
import {generateReviewByGemini} from "./gemini/GeminiClient";

const logger = require('./winston/logger');

const updateLabel = async (number: number): Promise<boolean> => {
    return addLabels(number)
        .then(() => {
            core.info(`PR #${number}에 라벨을 성공적으로 추가했습니다.`);
            logger.info(`Label successfully added to PR #${number}.`);
            return true;
        })
        .catch(error => {
            core.warning(`PR #${number}에 라벨 추가에 실패했습니다: ${error.message}`);
            logger.error(`Failed to add label to PR #${number}: ${error.message}`);
            throw error;
  });
};


async function run() {
  try {
    initialize();
    
    // PR 목록을 가져옴
    const { data: pulls } = await octokit.rest.pulls.list({
      owner: global.owner,
      repo: global.repo,
      state: 'open'
    });

    // PR이 없을 경우
    if(pulls.length === 0){
      core.info('열린 PR이 없습니다.');
      logger.info('No open pull requests found.');
      return;
    }

    const now = new Date();
    

    for (const pull of pulls) {
      const createdAt = new Date(pull.created_at);
      const diffInHours = (now.getTime() - createdAt.getTime()) / (1000 * 60 * 60);
      
      // PR이 생성된지 1시간이 지났을 경우
      if (diffInHours >= 1) {
        const { data: reviews } = await octokit.rest.pulls.listReviews({
          owner,
          repo,
          pull_number: pull.number
        });
        
        // PR에 리뷰가 없을 경우 -> AI 리뷰를 요청해야 함
        if (reviews.length === 0) {
          core.info(`PR #${pull.number}에 리뷰가 없습니다.`);
          logger.info(`No reviews found on PR #${pull.number}.`);

          // PR의 변경된 파일 정보 목록을 가져옴
          const changedFiles = await octokit.rest.pulls.listFiles({
              owner,
              repo,
              pull_number: pull.number,
            });
          
            // PR의 변경된 파일들을 string 형태로 가져옴
          const blobContentPromises = changedFiles.data.map(async file =>  await octokit.rest.git.getBlob({
            owner,
            repo,
            file_sha: file.sha,
            }).then(blob => blob.data.content));

            const blobContents = await Promise.all(blobContentPromises);

          // PR의 변경된 파일들을 AI 리뷰 요청
          const reviews = await generateReviewByGemini(blobContents);

        for (const review of reviews) {
            await octokit.rest.pulls.createReview({
              owner,
              repo,
              pull_number: pull.number,
              event: 'COMMENT',
              body: review
            });
          
        }

        core.info(`PR #${pull.number}에 리뷰를 남겼습니다.`);
        logger.info(`Review submitted on PR #${pull.number}.`);

        // PR에 라벨 추가
        Promise.all([
              updateLabel(pull.number)
            ]);

        }else{
          core.info(`PR #${pull.number}에 이미 리뷰가 남겨졌습니다.`);
          logger.info(`A review has already been submitted on PR #${pull.number}.`);
        }
      }else{
        core.info(`PR #${pull.number}는 1시간이 지나지 않았습니다. 현재 경과 시간 : ${Math.round(diffInHours / 0.0167)}분`);
        logger.info(`PR #${pull.number} has not passed the 1-hour threshold yet. Elapsed time: ${Math.round(diffInHours / 0.0167)} minutes.`);
      }
    }
  } catch (error: any) {
    core.setFailed(error.message);
    logger.error(error.message);
  }
}

run();

