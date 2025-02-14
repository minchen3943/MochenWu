import "@/styles/homePage.css";
export default async function page() {
  // 移动端文字
  const welcome = ["W", "e", "l", "c", "o", "m", "e", ","];
  const emoji = ["ദ", "്", "ദ", "ി", "˶", "•", "̀", "֊", "•", "́", ")", "✧"];
  const line2 = [
    "这",
    "里",
    "是",
    "瞑",
    "尘",
    "和",
    "沫",
    "鸯",
    "的",
    "博",
    "客",
  ];
  const line3 = ["在", "这", "里", "记", "录", "逛", "街", "、", "旅", "行"];
  const line4 = ["还", "有", "一", "些", "小", "故", "事", "～"];

  // 桌面端文字
  const desktopLine1 = [
    "W",
    "e",
    "l",
    "c",
    "o",
    "m",
    "e",
    ",",
    "这",
    "里",
    "是",
    "瞑",
    "尘",
    "和",
    "沫",
    "鸯",
    "的",
    "博",
    "客",
  ];

  return (
    <>
      <div className="flex flex-col lg:flex-row w-[90vw] lg:w-[80vw] lg:h-[94vh] mx-[5vw] lg:mx-[10vw]">
        <div className="lg:w-[40vw] h-[17.5vh] mt-[29vh] ml-[5vw] text-2xl font-normal font-sans">
          <div className="lg:hidden flex flex-col">
            <div className="flex items-center">
              <ul className="flex flex-wrap gap-[0.2rem] z-10">
                {welcome.map((char, index) => (
                  <li
                    key={`welcome-${index}`}
                    className={`animate-fade-up opacity-0 delay-${index}`}>
                    {char}
                  </li>
                ))}
              </ul>
              <div className="flex rounded-lg p-1 ml-[0.5rem]">
                {emoji.map((char, index) => (
                  <span
                    key={`emoji-${index}`}
                    className={`animate-fade-up opacity-0 delay-${
                      index + welcome.length
                    }`}>
                    {char}
                  </span>
                ))}
              </div>
            </div>

            <ul className="flex flex-wrap gap-[0.2rem] z-10 mt-4">
              {line2.map((char, index) => (
                <li
                  key={`line2-${index}`}
                  className={`animate-fade-up opacity-0 delay-${
                    index + welcome.length + emoji.length
                  }`}>
                  {char}
                </li>
              ))}
            </ul>

            <ul className="flex flex-wrap gap-[0.2rem] z-10 mt-4">
              {line3.map((char, index) => (
                <li
                  key={`line3-${index}`}
                  className={`animate-fade-up opacity-0 delay-${
                    index + welcome.length + emoji.length + line2.length
                  }`}>
                  {char}
                </li>
              ))}
            </ul>

            <ul className="flex flex-wrap gap-[0.2rem] z-10 mt-4">
              {line4.map((char, index) => (
                <li
                  key={`line4-${index}`}
                  className={`animate-fade-up opacity-0 delay-${
                    index +
                    welcome.length +
                    emoji.length +
                    line2.length +
                    line3.length
                  }`}>
                  {char}
                </li>
              ))}
            </ul>
          </div>

          <div className="hidden lg:block">
            <div className="flex items-center">
              <ul className="flex flex-wrap gap-[0.2rem] z-10">
                {desktopLine1.map((char, index) => (
                  <li
                    key={`desktop-${index}`}
                    className={`animate-fade-up opacity-0 delay-${index}`}>
                    {char}
                  </li>
                ))}
              </ul>
              <div className="flex hover:bg-[#d6c6e1a4] rounded-lg p-1 ml-[0.5rem]">
                {emoji.map((char, index) => (
                  <span
                    key={`emoji-${index}`}
                    className={`animate-fade-up opacity-0 delay-${
                      index + desktopLine1.length
                    }`}>
                    {char}
                  </span>
                ))}
              </div>
            </div>

            <ul className="flex flex-wrap gap-[0.2rem] z-10 mt-4">
              {line3.map((char, index) => (
                <li
                  key={`desktop-line2-${index}`}
                  className={`animate-fade-up opacity-0 delay-${
                    index + desktopLine1.length + emoji.length
                  }`}>
                  {char}
                </li>
              ))}
            </ul>

            <ul className="flex flex-wrap gap-[0.2rem] z-10 mt-2">
              {line4.map((char, index) => (
                <li
                  key={`desktop-line3-${index}`}
                  className={`animate-fade-up opacity-0 delay-${
                    index + desktopLine1.length + emoji.length + line3.length
                  }`}>
                  {char}
                </li>
              ))}
            </ul>
          </div>
        </div>

        <div className="h-6 w-6 absolute m-auto mt-[95vh] top-0 bottom-0 left-0 right-0">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={1.5}
            stroke="currentColor"
            className="size-6 animate-bounce">
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="m19.5 8.25-7.5 7.5-7.5-7.5"
            />
          </svg>
        </div>
        <div className="w-[60vw] lg:w-[15vw] h-[40vh] lg:h-[25vh] bg-[#D6C6E1] p-1 lg:mt-[45vh] lg:ml-[20vw] mt-[54vh] rounded-2xl">
          test
        </div>
      </div>
    </>
  );
}
