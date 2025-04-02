"use client";

import { useEffect, useState } from "react";
import { Link } from "next-view-transitions";
import config from "../../mcw-config.json";
import Loader from "./Loader";
import axios from "axios";

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
  const [maxPageNumber, setMaxPageNumber] = useState<number>(1);
  let pageSize = 9;

  useEffect(() => {
    const intersectionObserver = new IntersectionObserver((entries) => {
      if (entries[0].intersectionRatio <= 0) return;
      setTimeout(() => {
        setPageNumber(pageNumber + 0.5);
      });
    });

    const h2Element = document.querySelector("#pageChange");
    if (h2Element) {
      intersectionObserver.observe(h2Element);
    }
  });

  useEffect(() => {
    async function getArticleByPage(pageNumber: number) {
      const articleData = await axios.get(
        `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/article/get/page?page=${pageNumber}&pageSize=${pageSize}`
      );
      const article = await articleData.data;
      if (article.code === 200) {
        setArticleArray((articleArray) => {
          const filteredNewArticles = article.data.filter(
            (newArticle: Article) =>
              !articleArray.some(
                (existingArticle) =>
                  existingArticle.articleId === newArticle.articleId
              )
          );
          return [...articleArray, ...filteredNewArticles];
        });
      } else {
        console.error("获取文章失败：", article);
      }
    }
    getArticleByPage(Math.round(pageNumber));
  }, [pageNumber]);

  useEffect(() => {
    async function getArticlePageNumber(pageSize: number) {
      const maxPageNumberData = await axios.get(
        `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/article/get/page/number?pageSize=${pageSize}`
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
    <div className="flex flex-col bg-slate-600、50 w-full">
      {articleArray.map((article) => (
        <Link
          href={`/blog/${article.articleId}`}
          key={article.articleId}
          className="bg-[#e8d4f9d6] lg:bg-[#e8d4f961] lg:hover:bg-[#] w-full h-24 p-4 pl-4 my-3 rounded-xl hover:drop-shadow-lg">
          <h2 className="relative break-words text-2xl font-medium">
            {article.articleTitle}
          </h2>
          <div className="flex select-none flex-wrap items-center justify-end gap-4 mt-2">
            <div className="flex min-w-0 shrink grow flex-wrap gap-2 text-base">
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
                <span>{article.articleVisitorCount}</span>
              </div>
            </div>
          </div>
        </Link>
      ))}
      <div
        className={`w-full px-[37vw] lg:px-[28vw] ${
          pageNumber >= maxPageNumber ? "hidden" : ""
        }`}>
        <Loader />
      </div>
      <div
        className={`h-4 lg:h-12 ${pageNumber >= maxPageNumber ? "hidden" : ""}`}
        id="pageChange"></div>
    </div>
  );
}
