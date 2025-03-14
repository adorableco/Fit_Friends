
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
