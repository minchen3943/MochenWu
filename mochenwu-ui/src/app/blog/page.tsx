import { Metadata } from "next";

export const metadata: Metadata = {
  title: "沫尘屋-博客",
  openGraph: {
    title: "沫尘屋-博客",
  },
};

export default function page() {
  const apiTest = [
    {
      articleId: 1,
      articleTitle: "做感义工米。",
      articleUrl:
        "https://mcw-article.oss-cn-chengdu.aliyuncs.com/01ed3603-60b3-48c5-9a1b-342cf1f15e4d.md",
      articleDate: "2025-02-25T16:25:55",
      articleVisitorCount: 0,
      articleStatus: 1,
    },
    {
      articleId: 2,
      articleTitle: "响发第无然农林。",
      articleUrl:
        "https://mcw-article.oss-cn-chengdu.aliyuncs.com/1434ca9d-2f8a-4fee-b3ac-5eeff4bd6cb7.md",
      articleDate: "2025-02-25T16:26:26",
      articleVisitorCount: 0,
      articleStatus: 1,
    },
    {
      articleId: 3,
      articleTitle: "其王候到则。",
      articleUrl:
        "https://mcw-article.oss-cn-chengdu.aliyuncs.com/7783ed15-1038-49c7-bdfe-e0dc67bbec97.md",
      articleDate: "2025-02-25T16:26:37",
      articleVisitorCount: 0,
      articleStatus: 1,
    },
  ];
  return (
    <>
      <div className="w-[60vw] mx-[20vw] bg-slate-400 font-normal font-sans">
        <div className="flex flex-col bg-slate-600、50 w-full gap-6">
          {apiTest.map((article) => (
            <div
              key={article.articleId}
              className="bg-slate-700 w-full h-24 p-3 pl-4 rounded-lg">
              <h2 className="relative break-words text-2xl font-medium">
                {article.articleTitle}
              </h2>
              <div className="flex select-none flex-wrap items-center justify-end gap-4 mt-4">
                <div className="flex min-w-0 shrink grow flex-wrap gap-2 text-sm">
                  <div className="flex min-w-0 items-center space-x-1">
                    <span className="pr-1">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        strokeWidth={1.5}
                        stroke="currentColor"
                        className="size-4">
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          d="M12 6v6h4.5m4.5 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
                        />
                      </svg>
                    </span>
                    {article.articleDate}
                  </div>
                  <div className="flex min-w-0 items-center space-x-1">
                    <span>
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        strokeWidth={1.5}
                        stroke="currentColor"
                        className="size-4">
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z"
                        />
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"
                        />
                      </svg>
                    </span>
                    {article.articleVisitorCount}
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}
