export type TResponseData<TReq extends (...args: any[]) => Promise<{data: any}>> = Awaited<ReturnType<TReq>>["data"];

export type TAddLabelsData = TResponseData<typeof global.octokit.rest.issues.addLabels>;