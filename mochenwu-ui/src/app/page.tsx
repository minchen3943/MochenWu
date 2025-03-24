import AnimatedContent from "@/components/AnimatedContent";
import "@/styles/homePage.css";
export default async function page() {
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
      <div className="flex flex-col lg:flex-row w-[90vw] lg:w-[80vw] lg:h-[94vh] mx-[5vw] lg:mx-[10vw] font-normal font-sans">
        <div className="lg:w-[40vw] h-[17.5vh] mt-[29vh] ml-[5vw] text-2xl">
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

        <div className="lg:hidden h-6 w-6 absolute m-auto mt-[95vh] top-0 bottom-0 left-0 right-0">
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
        <AnimatedContent
          distance={50}
          direction="vertical"
          reverse={false}
          config={{ tension: 50, friction: 25 }}
          delay={1000}
          initialOpacity={0.0}
          animateOpacity={true}
          scale={0.7}
          threshold={0.1}>
          <div className="w-[60vw] lg:w-[15vw] h-[40vh] lg:h-[25vh] bg-[#e8d4f9d6] p-3 ml-[15vw] lg:ml-[20vw] mt-[54vh] lg:mt-[45vh] rounded-2xl drop-shadow-lg">
            <ul className="flex flex-col w-full h-full gap-3">
              <li className="w-full h-[20%] text-center pt-2 text-lg">
                网站访问人数
                <span className="ml-2 px-1 bg-[#9a73b54e] rounded-md drop-shadow-md">
                  8888
                </span>
              </li>
              <li className="h-[20%] pt-2 text-lg place-self-center drop-shadow-lg">
                <button
                  type="button"
                  className="flex rounded-md py-1 px-3 bg-[#8c53b59f] hover:bg-[#8c53b558]">
                  喜欢本站
                  <span className="ml-2 my-1 rounded-lg ">
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
                  <span className="ml-2 px-1 bg-[#8c5fbfa0] rounded-md">8</span>
                </button>
              </li>
              <li className="w-full h-[20%] text-center pt-2 text-lg"></li>
              <li className="w-full h-[20%] text-center pt-2 text-lg"></li>
            </ul>
          </div>
        </AnimatedContent>
      </div>
    </>
  );
}
