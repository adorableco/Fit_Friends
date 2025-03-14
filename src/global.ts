import type {getOctokit} from "@actions/github";

declare global {
    var owner: string;
    var repo: string;
    var octokit: ReturnType<typeof getOctokit>;
}

export {};
