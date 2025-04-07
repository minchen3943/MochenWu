"use client";

import React, { useState, useEffect } from "react";
import ReactMarkdown from "react-markdown";
import rehypeRaw from "rehype-raw";
import rehypeStringify from "rehype-stringify";
import rehypeHighlight from "rehype-highlight";
import remarkGfm from "remark-gfm";
import remarkParse from "remark-parse";
import remarkRehype from "remark-rehype";
import config from "../../mcw-config.json";
import "highlight.js/styles/github.css";
import "@/styles/markdown.scss";
import axios from "axios";

const Markdown: React.FC<{ articleId: number }> = ({ articleId }) => {
  const [blogContent, setBlogContent] = useState<string | null>(null);
  const [blogTitle, setBlogTitle] = useState<string | null>(null);
  const [blogTime, setBlogTime] = useState<string | null>(null);
  const [blogVisitorCount, setBlogVisitorCount] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<boolean>(false);

  function formatDate(dateString: string) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const weekdays = ["日", "一", "二", "三", "四", "五", "六"];
    const weekday = weekdays[date.getDay()];
    return `${year}年${month}月${day}日 星期${weekday}`;
  }

  useEffect(() => {
    setLoading(true);

    axios
      .get(
        `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/api/article/get/id?articleId=${articleId}`,
      )
      .then((response) => response.data)
      .then(async (data) => {
        if (data.code === 200 && data.data?.articleUrl) {
          const response = await axios.get(data.data.articleUrl);
          const markdownContent = response.data;
          setBlogTitle(data.data?.articleTitle);
          setBlogTime(formatDate(data.data?.articleDate));
          setBlogVisitorCount(data.data?.articleVisitorCount);
          return setBlogContent(markdownContent);
        } else {
          throw new Error("文章数据无效");
        }
      })
      .catch((error) => {
        console.error("加载文章失败:", error);
        setError(true);
        setBlogContent("无法加载文章内容");
      })
      .finally(() => setLoading(false));
  }, [articleId]);

  useEffect(() => {
    axios
      .put(
        `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/api/article/visitorAdd`,
        {},
        {
          params: { articleId },
          headers: {
            "Content-Type": "application/json",
          },
        },
      )
      .then((response) => response.data)
      .then(async (data) => {
        if (data.code === 200 && data.data?.articleUrl) {
          const response = await axios.get(data.data.articleUrl);
          const markdownContent = response.data;
          setBlogTitle(data.data?.articleTitle);
          setBlogTime(formatDate(data.data?.articleDate));
          setBlogVisitorCount(data.data?.articleVisitorCount);
          return setBlogContent(markdownContent);
        } else {
          throw new Error("文章数据无效");
        }
      });
  }, [articleId]);

  return (
    <>
      <div className={`px-8 pt-12 ${error || loading ? "hidden" : ""}`}>
        <div>
          <div className="markdown-title">{blogTitle}</div>
        </div>
        <div className="absolute left-0 right-0 m-auto h-fit w-fit">
          <div className="flex items-center gap-2 text-sm">
            <div className="flex items-center space-x-1">
              <span className="pt-[0.125rem]">
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
              <span className="pt-1">{blogTime}</span>
            </div>
            <div className="flex items-center space-x-1">
              <span className="pt-[0.125rem]">
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
              <span className="pt-1">{blogVisitorCount}</span>
            </div>
          </div>
        </div>
      </div>
      <div className={`markdown-body ${error || loading ? "text-center" : ""}`}>
        {loading ? (
          <p>正在加载内容...</p>
        ) : (
          <ReactMarkdown
            remarkPlugins={[
              remarkGfm,
              remarkParse,
              [remarkRehype, { allowDangerousHtml: true }],
            ]}
            rehypePlugins={[rehypeRaw, rehypeStringify, rehypeHighlight]}
            components={{
              a: ({ href, children, ...props }) => {
                return (
                  <a
                    href={href}
                    target="_blank"
                    rel="noopener noreferrer"
                    {...props}
                  >
                    {children}
                  </a>
                );
              },
              img: ({ src, ...props }) => {
                return <img src={src} alt="图片加载异常" {...props} />;
              },
              code({ className, children }) {
                const match = /language-(\w+)/.exec(className || "");
                if (match) {
                  const codeId = `code-${Math.random().toString(36).slice(2)}`;
                  const [click, setClick] = useState<boolean>(false);
                  useEffect(() => {
                    setTimeout(() => {
                      setClick(false);
                    }, 1500);
                  }, [click]);
                  return (
                    <div className="code-block">
                      <div className="code-header">
                        <span className="language-name">
                          {match[1].charAt(0).toUpperCase() + match[1].slice(1)}
                        </span>
                        <button
                          title="copy"
                          type="button"
                          onClick={() => {
                            const codeElement = document.getElementById(codeId);
                            if (codeElement) {
                              const textToCopy = codeElement.textContent || "";
                              if (
                                navigator.clipboard &&
                                navigator.clipboard.writeText
                              ) {
                                navigator.clipboard
                                  .writeText(textToCopy)
                                  .catch((err) =>
                                    console.error("复制失败:", err),
                                  );
                              } else {
                                // 回退方案：使用 execCommand
                                const textArea =
                                  document.createElement("textarea");
                                textArea.value = textToCopy;
                                document.body.appendChild(textArea);
                                textArea.select();
                                try {
                                  document.execCommand("copy");
                                } catch (err) {
                                  console.error("回退复制失败:", err);
                                }
                                document.body.removeChild(textArea);
                              }
                            }
                            setClick(true);
                          }}
                          className="copy-button"
                        >
                          {click ? (
                            <svg
                              xmlns="http://www.w3.org/2000/svg"
                              fill="none"
                              viewBox="0 0 24 24"
                              strokeWidth={1.5}
                              stroke="currentColor"
                              className="size-5"
                            >
                              <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                d="m4.5 12.75 6 6 9-13.5"
                              />
                            </svg>
                          ) : (
                            <svg
                              xmlns="http://www.w3.org/2000/svg"
                              fill="none"
                              viewBox="0 0 24 24"
                              strokeWidth={1.5}
                              stroke="currentColor"
                              className="size-5"
                            >
                              <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                d="M9 12h3.75M9 15h3.75M9 18h3.75m3 .75H18a2.25 2.25 0 0 0 2.25-2.25V6.108c0-1.135-.845-2.098-1.976-2.192a48.424 48.424 0 0 0-1.123-.08m-5.801 0c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 0 0 .75-.75 2.25 2.25 0 0 0-.1-.664m-5.8 0A2.251 2.251 0 0 1 13.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m0 0H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V9.375c0-.621-.504-1.125-1.125-1.125H8.25ZM6.75 12h.008v.008H6.75V12Zm0 3h.008v.008H6.75V15Zm0 3h.008v.008H6.75V18Z"
                              />
                            </svg>
                          )}
                        </button>
                      </div>
                      <pre>
                        <code id={codeId} className={className}>
                          {children}
                        </code>
                      </pre>
                    </div>
                  );
                }
                return <code className={className}>{children}</code>;
              },
            }}
          >
            {blogContent}
          </ReactMarkdown>
        )}
      </div>
    </>
  );
};

export default Markdown;
