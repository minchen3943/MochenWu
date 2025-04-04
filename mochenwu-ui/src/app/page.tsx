import AnimatedContent from "@/components/AnimatedContent";
import StatusBar from "@/components/StatusBar";
import "@/styles/homePage.scss";
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
      <div className="mx-[5vw] flex w-[90vw] flex-col items-center font-medium lg:mx-[10vw] lg:h-[94vh] lg:w-[80vw] lg:flex-row lg:items-stretch">
        <div className="mt-[29vh] text-2xl lg:h-[17.5vh] lg:w-[40vw]">
          <div className="flex flex-col lg:hidden">
            <div className="flex items-center">
              <ul className="z-10 flex flex-wrap gap-[0.2rem]">
                {welcome.map((char, index) => (
                  <li
                    key={`welcome-${index}`}
                    className={`animate-fade-up opacity-0 delay-${index}`}
                  >
                    {char}
                  </li>
                ))}
              </ul>
              <div className="fade-in-bg ml-[0.5rem] flex rounded-lg bg-[#d6c6e1a4] px-1 lg:p-1">
                {emoji.map((char, index) => (
                  <span
                    key={`emoji-${index}`}
                    className={`animate-fade-up opacity-0 delay-${
                      index + welcome.length
                    }`}
                  >
                    {char}
                  </span>
                ))}
              </div>
            </div>

            <ul className="z-10 mt-4 flex flex-wrap gap-[0.2rem]">
              {line2.map((char, index) => (
                <li
                  key={`line2-${index}`}
                  className={`animate-fade-up opacity-0 delay-${
                    index + welcome.length + emoji.length
                  }`}
                >
                  {char}
                </li>
              ))}
            </ul>

            <ul className="z-10 mt-4 flex flex-wrap gap-[0.2rem]">
              {line3.map((char, index) => (
                <li
                  key={`line3-${index}`}
                  className={`animate-fade-up opacity-0 delay-${
                    index + welcome.length + emoji.length + line2.length
                  }`}
                >
                  {char}
                </li>
              ))}
            </ul>

            <ul className="z-10 mt-4 flex flex-wrap gap-[0.2rem]">
              {line4.map((char, index) => (
                <li
                  key={`line4-${index}`}
                  className={`animate-fade-up opacity-0 delay-${
                    index +
                    welcome.length +
                    emoji.length +
                    line2.length +
                    line3.length
                  }`}
                >
                  {char}
                </li>
              ))}
            </ul>
          </div>

          <div className="hidden lg:block">
            <div className="flex items-center">
              <ul className="z-10 flex flex-wrap gap-[0.2rem]">
                {desktopLine1.map((char, index) => (
                  <li
                    key={`desktop-${index}`}
                    className={`animate-fade-up opacity-0 delay-${index}`}
                  >
                    {char}
                  </li>
                ))}
              </ul>
              <div className="ml-[0.5rem] flex rounded-lg p-1 hover:bg-[#d6c6e1a4]">
                {emoji.map((char, index) => (
                  <span
                    key={`emoji-${index}`}
                    className={`animate-fade-up opacity-0 delay-${
                      index + desktopLine1.length
                    }`}
                  >
                    {char}
                  </span>
                ))}
              </div>
            </div>

            <ul className="z-10 mt-2 flex flex-wrap gap-[0.2rem]">
              {line3.map((char, index) => (
                <li
                  key={`desktop-line2-${index}`}
                  className={`animate-fade-up opacity-0 delay-${
                    index + desktopLine1.length + emoji.length
                  }`}
                >
                  {char}
                </li>
              ))}
            </ul>

            <ul className="z-10 mt-2 flex flex-wrap gap-[0.2rem]">
              {line4.map((char, index) => (
                <li
                  key={`desktop-line3-${index}`}
                  className={`animate-fade-up opacity-0 delay-${
                    index + desktopLine1.length + emoji.length + line3.length
                  }`}
                >
                  {char}
                </li>
              ))}
            </ul>
          </div>
        </div>
        <div className="absolute bottom-0 left-0 right-0 top-0 m-auto mt-[95vh] h-6 w-6 lg:hidden">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={1.5}
            stroke="currentColor"
            className="size-6 animate-bounce"
          >
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
          threshold={0.1}
        >
          <StatusBar />
        </AnimatedContent>
      </div>
    </>
  );
}
