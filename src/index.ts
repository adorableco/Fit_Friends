
import * as core from "@actions/core";
import * as github from '@actions/github';
import { addLabels } from "./api";

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
    const token = process.env.GITHUB_TOKEN;
    if (!token) {
      throw new Error('GITHUB_TOKEN이 설정되지 않았습니다.');
    }
    const octokit = github.getOctokit(token);
    const { owner, repo } = github.context.repo;
    
    const { data: pulls } = await octokit.rest.pulls.list({
      owner,
      repo,
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
        
          await octokit.rest.pulls.createReview({
            owner,
            repo,
            pull_number: pull.number,
            event: 'COMMENT',
            body: '@coderabbitai review 자동 리뷰: 이 PR은 1시간 동안 리뷰가 없는 상태입니다. coderabbit이 리뷰를 남깁니다.'
          });
          core.info(`PR #${pull.number}에 리뷰를 남겼습니다.`);
          
          Promise.all([
            updateLabel(pull.number)
          ]);
        }
      }
    }
  } catch (error: any) {
    core.setFailed(error.message);
  }
}

run();

