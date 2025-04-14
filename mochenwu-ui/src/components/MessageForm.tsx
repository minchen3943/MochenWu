"use client";
import { useForm } from "react-hook-form";
import axios from "axios";
import config from "../../mcw-config.json";
import { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane, faSpinner } from "@fortawesome/free-solid-svg-icons";
import SucceedMessage from "@/components/SucceedMessage";

export default function MessageForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
    watch,
  } = useForm({ mode: "onChange" });
  const [mounted, setMounted] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState(false);
  const [ip, setIp] = useState("");

  useEffect(() => {
    setMounted(true);
  }, []);

  useEffect(() => {
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
        `${config.server.axios.protocol}://${config.server.axios.host}/api/comment/add`,
        payload,
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
    <form onSubmit={handleSubmit(onSubmit)}>
      <div className="">
        <textarea
          {...register("commentContent", {
            required: true,
            maxLength: 1000,
          })}
          placeholder="说点什么好呢~ *"
          className={`not-placeholder-shown:font-sans h-24 w-full resize-none rounded-xl border border-gray-300 p-3 focus-visible:outline-[#c2a1dbcf]`}
        ></textarea>
      </div>
      <div className="mt-3 flex">
        <input
          {...register("commentUserName", {
            required: true,
            maxLength: 20,
          })}
          placeholder="昵称*  (不能大于20字符)"
          className={`not-placeholder-shown:font-sans mr-[2.5vw] h-11 w-[42.5vw] rounded-xl border border-gray-300 px-2 text-sm focus-visible:outline-[#c2a1dbcf] lg:mr-[2vw] lg:h-9 lg:w-[25vw] lg:px-3 lg:text-base`}
        />
        <input
          {...register("commentUserEmail", {
            pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
            maxLength: 40,
          })}
          placeholder="邮箱  (不能大于40字符)"
          className={`not-placeholder-shown:font-sans ml-[2.5vw] h-11 w-[42.5vw] rounded-xl border px-2 text-sm focus-visible:outline-[#c2a1dbcf] lg:ml-[2vw] lg:h-9 lg:w-[25vw] lg:px-3 lg:text-base ${
            watch("commentUserEmail") && errors.commentUserEmail
              ? "border-red-500"
              : "border-gray-300"
          }`}
        />
      </div>
      <button
        title="Submit Form"
        type="submit"
        disabled={loading}
        className="ml-[83.5vw] px-2 py-1 text-[#9A73B5] lg:ml-[52vw] lg:py-2"
      >
        {loading ? (
          <FontAwesomeIcon icon={faSpinner} className="animate-spin" />
        ) : (
          <FontAwesomeIcon icon={faPaperPlane} />
        )}
      </button>
      {errorMessage && (
        <div className="rounded-lg bg-red-600 py-1 text-center">
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
  );
}
