"use client";

import { useState, useEffect } from "react";

export default function StatusBar() {
  const [visitors, setVisitors] = useState("");
  const [likes, setLikes] = useState("");
  const [likeEdit, setLikeEdit] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        const currentTime = Date.now();
        const lastVisitTime = Number(localStorage.getItem("lastVisitTime"));
        console.log(currentTime - lastVisitTime);
        console.log(lastVisitTime);
        console.log(currentTime);
        if (lastVisitTime && currentTime - lastVisitTime < 30000) {
          const visitorData = await fetch(
            "http://localhost:8080/data/visitor/get"
          );
          const visitor = await visitorData.json();
          if (visitor.code === 200) {
            setVisitors(visitor.data);
            localStorage.setItem("lastVisitTime", currentTime.toString());
          } else {
            setVisitors("null");
          }
        } else {
          const visitorData = await fetch(
            "http://localhost:8080/data/visitor/add",
            {
              method: "PUT",
            }
          );
          const visitor = await visitorData.json();
          if (visitor.code === 200) {
            setVisitors(visitor.data);
            localStorage.setItem("lastVisitTime", currentTime.toString());
          } else {
            setVisitors("null");
          }
        }
        const likeData = await fetch("http://localhost:8080/data/like/get");
        const like = await likeData.json();
        setLikes(like.data);
      } catch (error) {
        console.error("数据获取失败:", error);
        setVisitors("error");
        setLikes("error");
      }
    }
    fetchData();
  }, [likes]);
  const likeAdd = async () => {
    if (likeEdit) {
      try {
        const response = await fetch("http://localhost:8080/data/like/add", {
          method: "PUT",
        });
        const data = await response.json();
        if (data.code === 200) {
          setLikes(data.data); // 更新点赞数
        } else {
          console.error("点赞失败:", data.message);
        }
      } catch (error) {
        console.error("请求失败:", error);
      }
      setLikeEdit(false);
    }
  };
  return (
    <div className="w-[60vw] lg:w-[15vw] h-[40vh] lg:h-[25vh] p-3 ml-[15vw] lg:ml-[20vw] mt-[54vh] lg:mt-[45vh] rounded-2xl drop-shadow-lg">
      <ul className="lg:w-[10vw] flex flex-col h-full gap-6">
        <li className="text-lg place-self-center drop-shadow-lg">
          <span className="flex rounded-md py-1 px-3 bg-[#e8d4f9d6]">
            访问人数
            <span className="ml-2 px-1 bg-[#9a73b54e] rounded-md">
              {visitors}
            </span>
          </span>
        </li>
        <li className="text-lg place-self-center drop-shadow-xl">
          <button
            type="button"
            className="flex rounded-md py-1 px-3 bg-[#8c53b59f] lg:hover:bg-[#8c53b558] "
            onClick={likeAdd}>
            喜欢本站
            <span className="ml-2 my-1 rounded-lg">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
                className="size-5">
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z"
                />
              </svg>
            </span>
            <span className="ml-2 px-1 bg-[#8c5fbfa0] rounded-md">{likes}</span>
          </button>
        </li>
      </ul>
    </div>
  );
}
