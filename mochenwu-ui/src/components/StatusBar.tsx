"use client";
import { useState, useEffect } from "react";
import "@/styles/statusBar.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart } from "@fortawesome/free-solid-svg-icons";

export default function StatusBar() {
  const [visitors, setVisitors] = useState("");
  const [likes, setLikes] = useState("");
  const [likeEdit, setLikeEdit] = useState(true);
  const [likeColor, setLikeColor] = useState(false);

  useEffect(() => {
    async function fetchData() {
      try {
        const currentTime = Date.now();
        const lastVisitTime = Number(localStorage.getItem("lastVisitTime"));
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
  }, []);
  const likeAdd = async () => {
    if (likeEdit) {
      try {
        const response = await fetch("http://localhost:8080/data/like/add", {
          method: "PUT",
        });
        const data = await response.json();
        if (data.code === 200) {
          setLikes(data.data); // 更新点赞数
          setLikeColor(true);
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
      <ul className="lg:w-[13vw] flex flex-col h-full gap-6">
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
            className="button flex rounded-md py-1 px-3 bg-[#8c53b59f] lg:hover:bg-[#8c53b558] "
            onClick={likeAdd}>
            喜欢本站
            <span className="ml-2 rounded-lg">
              <FontAwesomeIcon
                icon={faHeart}
                style={likeColor ? { color: "#f13b3b" } : {}}
              />
            </span>
            <span className="ml-2 px-1 bg-[#8c5fbfa0] rounded-md">{likes}</span>
          </button>
        </li>
      </ul>
    </div>
  );
}
