"use client";

import { useEffect, useState } from "react";
import axios from "axios";
import config from "../../mcw-config.json";
import Loader from "./Loader";

interface Comment {
  commentId: number;
  commentContent: string;
  commentUserName: string;
  commentUserEmail: string;
  commentUserIp: string;
  commentDate: string;
  commentStatus: number;
}

export default function MessageList() {
  const [comments, setComments] = useState<Comment[]>([]);
  const [pageNumber, setPageNumber] = useState<number>(1);
  const [maxPageNumber, setMaxPageNumber] = useState<number>(1);
  let pageSize = 10;

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
    async function getCommentsByPage(pageNumber: number) {
      const commentData = await axios.get(
        `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/api/comment/get/page?page=${pageNumber}&pageSize=${pageSize}`
      );
      const commentResponse = await commentData.data;
      if (commentResponse.code === 200) {
        setComments((comments) => {
          const filteredNewComments = commentResponse.data.filter(
            (newComment: Comment) =>
              !comments.some(
                (existingComment) =>
                  existingComment.commentId === newComment.commentId
              )
          );
          return [...comments, ...filteredNewComments];
        });
      } else {
        console.error("获取评论失败：", commentResponse);
      }
    }
    getCommentsByPage(Math.round(pageNumber));
  }, [pageNumber]);

  useEffect(() => {
    async function getCommentPageNumber(pageSize: number) {
      const maxPageNumberData = await axios.get(
        `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/api/comment/get/page/number?pageSize=${pageSize}`
      );
      const maxPageNumbers = await maxPageNumberData.data;
      setMaxPageNumber(parseInt(maxPageNumbers.data));
    }
    getCommentPageNumber(pageSize);
  }, []);

  function formatDate(dateString: string) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const weekdays = ["日", "一", "二", "三", "四", "五", "六"];
    const weekday = weekdays[date.getDay()];
    return `${year}年${month}月${day}日 星期${weekday}`;
  }

  return (
    <div className="flex flex-col gap-3 bg-slate-600、50 w-full">
      {comments.map((comment) => (
        <div key={comment.commentId} className=" w-full h-auto my-1 rounded-xl">
          <div className="flex gap-2 items-center mt-2">
            <h2 className="text-xl font-semibold ml-1 lg:ml-2">
              {comment.commentUserName}
            </h2>
            <div className="flex min-w-0 items-center space-x-1 text-xs">
              <span>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth={1.5}
                  stroke="currentColor"
                  className="size-3">
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M12 6v6h4.5m4.5 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
                  />
                </svg>
              </span>
              <span>{formatDate(comment.commentDate)}</span>
            </div>
          </div>
          <p className="mt-2 ml-3 lg:ml-4 text-base break-words">
            <span className="bg-[#e8d4f961]  p-1 px-2 rounded-xl rounded-bl-sm">
              {comment.commentContent}
            </span>
          </p>
        </div>
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
