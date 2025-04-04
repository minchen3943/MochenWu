"use client";
import { useState, useEffect } from "react";
import "@/styles/statusBar.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import config from "../../mcw-config.json";
import axios from "axios";

export default function StatusBar() {
  const [visitors, setVisitors] = useState("");
  const [likes, setLikes] = useState("");
  const [likeEdit, setLikeEdit] = useState(true);
  const [buttonClick, setButtonClick] = useState(false);
  const [likeColor, setLikeColor] = useState(false);
  const [time, setTime] = useState<number>(null!);

  useEffect(() => {
    const calculateDays = () => {
      setTime(Math.floor((Date.now() - 1744214400000) / 86400000));
    };

    calculateDays();
  }, []);

  useEffect(() => {
    async function fetchData() {
      try {
        const currentTime = Date.now();
        const lastVisitTime = Number(localStorage.getItem("lastVisitTime"));
        if (lastVisitTime && currentTime - lastVisitTime < 30000) {
          const visitorData = await axios.get(
            `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/api/data/visitor/get`,
          );
          const visitor = await visitorData.data;
          if (visitor.code === 200) {
            setVisitors(visitor.data);
            localStorage.setItem("lastVisitTime", currentTime.toString());
          } else {
            setVisitors("null");
          }
        } else {
          const visitorData = await axios.put(
            `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/api/data/visitor/add`,
          );
          const visitor = await visitorData.data;
          if (visitor.code === 200) {
            setVisitors(visitor.data);
            localStorage.setItem("lastVisitTime", currentTime.toString());
          } else {
            setVisitors("null");
          }
        }
        const likeData = await axios.get(
          `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/api/data/like/get`,
        );
        const like = await likeData.data;
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
        const response = await axios.put(
          `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/api/data/like/add`,
        );
        const data = await response.data;
        if (data.code === 200) {
          setLikes(data.data);
          setLikeColor(true);
          setTimeout(() => {
            setButtonClick(true);
          }, 1000);
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
    <div className="mt-[54vh] h-[40vh] w-[60vw] rounded-2xl p-3 drop-shadow-lg lg:ml-[20vw] lg:mt-[45vh] lg:h-[25vh] lg:w-[15vw]">
      <ul className="flex h-full flex-col gap-6 lg:w-[13vw]">
        <li className="place-self-center text-lg drop-shadow-md">
          <span className="flex rounded-md bg-[#e8d4f9d6] px-3 py-1">
            <div className="flex min-w-36 justify-center">
              访问人数
              <span className="ml-2 rounded-md bg-[#9a73b54e] px-1">
                {visitors}
              </span>
            </div>
          </span>
        </li>
        <li className="place-self-center text-lg drop-shadow-md">
          <span className="flex rounded-md bg-[#e8d4f9d6] px-3 py-1">
            <div className="flex min-w-36 justify-center">
              本站已运行
              <span className="ml-2 rounded-md bg-[#9a73b54e] px-1">
                {time}
              </span>
              天
            </div>
          </span>
        </li>
        <li className="place-self-center text-lg drop-shadow-2xl">
          <button
            type="button"
            className={`${
              buttonClick ? "" : "button"
            } flex rounded-md bg-[#8c53b59f] px-3 py-1 lg:hover:bg-[#8c53b558]`}
            onClick={likeAdd}
          >
            <div className="flex min-w-36 justify-center">
              喜欢本站
              <span className="ml-2 rounded-lg">
                <FontAwesomeIcon
                  icon={faHeart}
                  style={likeColor ? { color: "#f13b3b" } : {}}
                />
              </span>
              <span className="ml-2 rounded-md bg-[#8c5fbfa0] px-1">
                {likes}
              </span>
            </div>
          </button>
        </li>
      </ul>
    </div>
  );
}
