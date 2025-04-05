
import type {TAddLabelsData} from "./types/github";


export const addLabels = async (number: number): Promise<TAddLabelsData> => {
    const {data: labels} = await global.octokit.rest.issues.addLabels({
        owner: global.owner,
        repo: global.repo,
        issue_number: number,
        labels: ["AIReview"],
    });

    return labels;
};

export const extractReviewLinesFromPatch = (patch: string) => {
  const lines = patch.split('\n');
  const reviewLines = [];

  for (const line of lines) {
    if (line.startsWith('+++') || line.startsWith('---')) continue; 
    if (line.startsWith('@@')) continue; 
    if (line.startsWith('+') && !line.startsWith('+++')) {
      const code = line.slice(1);
      if (code.trim() === '') continue; 

      if (/^\s*(\/\/|#|\/\*|\*)/.test(code)) continue;
      reviewLines.push(code + '\n');
    }
  }
  return reviewLines.join('');
}