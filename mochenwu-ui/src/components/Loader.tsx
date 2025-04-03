import React from "react";
import "@/styles/Loader.css"; // 引入 CSS 文件

const Loader = () => {
  return (
    <div className="styled-wrapper">
      <svg viewBox="25 25 50 50">
        <circle r={20} cy={50} cx={50} />
      </svg>
    </div>
  );
};

export default Loader;
