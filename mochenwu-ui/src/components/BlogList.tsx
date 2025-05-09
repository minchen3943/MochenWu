"use client";

import { useEffect, useState } from "react";
import { Link } from "next-view-transitions";
import config from "../../mcw-config.json";
import Loader from "./Loader";
import axios from "axios";
import LoaderAnimal from "./LoaderAnimal";

interface Article {
  articleId: number;
  articleTitle: string;
  articleUrl: string;
  articleDate: string;
  articleVisitorCount: number;
  articleStatus: number;
  articleName: string;
}

export default function BlogList() {
  const [articleArray, setArticleArray] = useState<Article[]>([]);
  const [pageNumber, setPageNumber] = useState<number>(1);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [maxPageNumber, setMaxPageNumber] = useState<number>(1);
  const pageSize = 9;

  useEffect(() => {
    const intersectionObserver = new IntersectionObserver((entries) => {
      if (entries[0].intersectionRatio <= 0) return;
      setPageNumber(pageNumber + 0.5);
    });

    const h2Element = document.querySelector("#pageChange");
    if (h2Element) {
      intersectionObserver.observe(h2Element);
    }
  });

  useEffect(() => {
    async function getArticleByPage(pageNumber: number) {
      const articleData = await axios
        .get(
          `${config.server.axios.protocol}://${config.server.axios.host}/api/article/get/page?page=${pageNumber}&pageSize=${pageSize}`,
        )
        .catch((error) => {
          console.error("获取文章失败：", error);
          setIsLoading(false);
        });
      if (!articleData) {
        setIsLoading(false);
        return;
      }
      const article = await articleData.data;
      if (article.code === 200) {
        setIsLoading(false);
        setArticleArray((articleArray) => {
          const filteredNewArticles = article.data.filter(
            (newArticle: Article) =>
              !articleArray.some(
                (existingArticle) =>
                  existingArticle.articleId === newArticle.articleId,
              ),
          );
          return [...articleArray, ...filteredNewArticles];
        });
      } else {
        setIsLoading(false);
        console.error("获取文章失败：", article);
      }
    }
    getArticleByPage(Math.round(pageNumber));
  }, [pageNumber]);

  useEffect(() => {
    async function getArticlePageNumber(pageSize: number) {
      const maxPageNumberData = await axios.get(
        `${config.server.axios.protocol}://${config.server.axios.host}/api/article/get/page/number?pageSize=${pageSize}`,
      );
      const maxPageNumbers = await maxPageNumberData.data;
      setMaxPageNumber(parseInt(maxPageNumbers.data));
    }
    getArticlePageNumber(pageSize);
  }, []);

  function formatDate(dateString: string) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = date.getMonth() + 1; // 月份从0开始，所以加1
    const day = date.getDate();
    const weekdays = ["日", "一", "二", "三", "四", "五", "六"];
    const weekday = weekdays[date.getDay()]; // 获取星期几
    return `${year}年${month}月${day}日 星期${weekday}`;
  }
  return (
    <div className="bg-slate-600、50 flex w-full flex-col">
      {isLoading ? (
        <div className="relative left-0 right-0 m-auto text-center">
          加载中.....
          <LoaderAnimal />
        </div>
      ) : null}
      {!isLoading && !articleArray?.length ? (
        <div className="relative left-0 right-0 m-auto text-red-600">
          加载错误，数据为空
        </div>
      ) : null}
      {articleArray.map((article) => (
        <Link
          href={`/blog/${article.articleId}`}
          key={article.articleId}
          className="my-3 h-24 w-full rounded-xl bg-[#e8d4f9d6] p-4 pl-4 hover:drop-shadow-lg lg:bg-[#e8d4f961] lg:hover:bg-[#]"
        >
          <h2 className="relative break-words text-2xl font-medium">
            {article.articleTitle}
          </h2>
          <div className="mt-2 flex select-none flex-wrap items-center justify-end gap-4">
            <div className="flex min-w-0 shrink grow flex-wrap gap-2 text-base">
              <div className="flex min-w-0 items-center space-x-1">
                <span>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth={1.5}
                    stroke="currentColor"
                    className="size-4"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      d="M12 6v6h4.5m4.5 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
                    />
                  </svg>
                </span>
                <span>{formatDate(article.articleDate)}</span>
              </div>
              <div className="flex min-w-0 items-center space-x-1">
                <span>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth={1.5}
                    stroke="currentColor"
                    className="size-4"
                  >
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
                <span>{article.articleVisitorCount}</span>
              </div>
            </div>
          </div>
        </Link>
      ))}
      <div
        id="pageChange"
        className={`h-24 w-full px-[37vw] lg:px-[28vw] ${
          pageNumber >= maxPageNumber ? "hidden" : ""
        }`}
      >
        <Loader />
      </div>
    </div>
  );
}
