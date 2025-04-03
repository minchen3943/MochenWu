"use client";
import { useForm } from "react-hook-form";
import axios from "axios";
import config from "../../../mcw-config.json";
import { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane, faSpinner } from "@fortawesome/free-solid-svg-icons";
import SucceedMessage from "@/components/SucceedMessage";
import MessageList from "@/components/MessageList";

export default function Page() {
  const [mounted, setMounted] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState(false);
  const [ip, setIp] = useState("");

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
    watch,
  } = useForm({ mode: "onChange" });

  useEffect(() => {
    setMounted(true);
  }, []);

  useEffect(() => {
    if (!mounted) return;

    const fetchIp = async () => {
      try {
        const response = await axios.get("https://ipinfo.io/json");
        setIp(response.data.ip);
      } catch (error) {
        console.error("获取 IP 失败", error);
      }
    };

    fetchIp();
  }, [mounted]);

  useEffect(() => {
    if (successMessage) {
      const timer = setTimeout(() => {
        setSuccessMessage(false);
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [successMessage]);

  const onSubmit = async (data: any) => {
    setLoading(true);
    setErrorMessage("");
    const payload = { ...data, commentUserIp: ip };
    try {
      await axios.post(
        `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/api/comment/add`,
        payload
      );
      setSuccessMessage(true);
      reset();
    } catch (error) {
      console.error("提交失败", error);
      setErrorMessage("提交失败，请检查输入信息并稍后再试重试！");
    } finally {
      setLoading(false);
    }
  };

  if (!mounted) {
    return null;
  }

  return (
    <div className="w-[90vw] lg:w-[54vw] mx-[5vw] lg:mx-[23vw] mt-[5vh] lg:mt-[7.5vh]">
      <div className="text-3xl font-semibold">留言板</div>
      <div className="mt-[3vh] text-lg font-normal">来都来了，说点什么吧~</div>
      <div className="lg:mt-[30vh]">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="flex my-5">
            <input
              {...register("commentUserName", {
                required: true,
                maxLength: 20,
              })}
              placeholder="昵称*  (不能大于20字符)"
              className={`w-[42.5vw] lg:w-[25vw] h-11 lg:h-9 mr-[2.5vw] lg:mr-[2vw] px-2 lg:px-3 rounded-xl border text-sm lg:text-base focus-visible:outline-[#c2a1dbcf] border-gray-300 [&:not(:placeholder-shown)]:font-sans`}
            />

            <input
              {...register("commentUserEmail", {
                pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                maxLength: 40,
              })}
              placeholder="邮箱  (不能大于40字符)"
              className={`w-[42.5vw] lg:w-[25vw] h-11 lg:h-9 ml-[2.5vw] lg:ml-[2vw] px-2 lg:px-3 rounded-xl border text-sm lg:text-base focus-visible:outline-[#c2a1dbcf] [&:not(:placeholder-shown)]:font-sans ${
                watch("commentUserEmail") && errors.commentUserEmail
                  ? "border-red-500"
                  : "border-gray-300"
              }`}
            />
          </div>
          <div className="">
            <textarea
              {...register("commentContent", {
                required: true,
                maxLength: 1000,
              })}
              placeholder="说点什么好呢~ *"
              className={`w-full h-24 p-3 rounded-xl border focus-visible:outline-[#c2a1dbcf] border-gray-300 resize-none [&:not(:placeholder-shown)]:font-sans`}></textarea>
          </div>
          <button
            title="Submit Form"
            type="submit"
            disabled={loading}
            className="px-2 py-1 ml-[83.5vw] lg:ml-[52vw] text-[#9A73B5]">
            {loading ? (
              <FontAwesomeIcon icon={faSpinner} className="animate-spin" />
            ) : (
              <FontAwesomeIcon icon={faPaperPlane} />
            )}
          </button>
          {errorMessage && (
            <div className="bg-red-600 rounded-lg text-center py-1">
              {errorMessage}
            </div>
          )}
          {successMessage && (
            <SucceedMessage
              message1="留言提交成功！"
              message2="等待审核完毕将会展示在页面上"
            />
          )}
        </form>
      </div>
      <div className="mt-20 lg:mt-40">
        <MessageList />
      </div>
    </div>
  );
}
