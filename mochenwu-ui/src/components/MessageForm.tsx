"use client";
import { useForm } from "react-hook-form";
import axios from "axios";
import config from "../../mcw-config.json";
import { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane, faSpinner } from "@fortawesome/free-solid-svg-icons";
import SucceedMessage from "./SucceedMessage";

export default function MessageForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
    watch,
  } = useForm({ mode: "onChange" });
  const [ip, setIp] = useState("");
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMassage, setSuccessMassage] = useState(false);

  useEffect(() => {
    axios
      .get("https://api64.ipify.org?format=json")
      .then((response) => setIp(response.data.ip))
      .catch((error) => console.error("获取 IP 失败", error));
  }, []);

  useEffect(() => {
    if (successMassage) {
      const timer = setTimeout(() => {
        setSuccessMassage(false);
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [successMassage]);
  const onSubmit = async (data: any) => {
    setLoading(true);
    setErrorMessage("");
    const payload = { ...data, commentUserIp: ip };
    try {
      await axios.post(
        `${config.server.axios.protocol}://${config.server.axios.host}:${config.server.axios.port}/comment/add`,
        payload
      );
      setSuccessMassage(true);
      reset();
    } catch (error) {
      console.error("提交失败", error);
      setErrorMessage("提交失败，请检查输入信息后重试！");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <div className="flex my-5">
        <input
          {...register("commentUserName", { required: true, maxLength: 20 })}
          placeholder="昵称*  (不能大于20个字符)"
          className={`w-[25vw] h-9 mr-[2vw] px-3 rounded-xl font-sans border focus-visible:outline-[#c2a1dbcf] border-gray-300`}
        />

        <input
          {...register("commentUserEmail", {
            pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
            maxLength: 40,
          })}
          placeholder="邮箱  (不能大于40个字符)"
          className={`w-[25vw] h-9 ml-[2vw] px-3 rounded-xl font-sans border focus-visible:outline-[#c2a1dbcf] ${
            watch("commentUserEmail") && errors.commentUserEmail
              ? "border-red-500"
              : "border-gray-300"
          }`}
        />
      </div>
      <div className="">
        <textarea
          {...register("commentContent", { required: true, maxLength: 1000 })}
          placeholder="说点什么好呢~ *"
          className={`w-full h-24 p-3 rounded-xl font-sans border focus-visible:outline-[#c2a1dbcf] border-gray-300 resize-none`}></textarea>
      </div>
      <button
        title="Submit Form"
        type="submit"
        disabled={loading}
        className="px-2 py-1 ml-[52vw] text-[#9A73B5]">
        {loading ? (
          <FontAwesomeIcon icon={faSpinner} className="animate-spin" />
        ) : (
          <FontAwesomeIcon icon={faPaperPlane} />
        )}
      </button>
      {errorMessage && <div>{errorMessage}</div>}
      {successMassage && (
        <SucceedMessage
          message1="评论提交成功！"
          message2="等待审核完毕将会展示在页面上"
        />
      )}
    </form>
  );
}
