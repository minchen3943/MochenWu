import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope } from "@fortawesome/free-regular-svg-icons";
import "@/styles/Footer.scss";

export default function Footer() {
  return (
    <div className="flex w-full flex-col border-t border-purple-300/50 bg-[#e6cfea72]">
      <div className="flex h-fit w-full pt-1 font-sans text-2xl text-[#e9b5e4dc] lg:justify-normal lg:px-44 lg:pt-5">
        <div className="flex h-fit w-fit flex-col items-start lg:flex-row lg:gap-0">
          <div className="relative">
            <span className="px-3 lg:p-0">Find us</span>
          </div>
          <div className="flex items-center justify-center">
            <span className="px-3">|</span>
            <span className="pt-1 text-lg font-normal">To 瞑尘</span>
            <ul className="inline-flex gap-3 pl-3">
              <li className="size-[1.875rem] lg:size-8">
                <a
                  href="https://github.com/minchen3943"
                  target="_blank"
                  rel="noopener noreferrer"
                  title="Github"
                >
                  <p className="flex size-[1.875rem] items-center justify-center rounded-full bg-black lg:size-8">
                    <svg
                      className="size-[0.875rem] lg:size-4"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="2597"
                    >
                      <path
                        d="M682.215454 981.446137c-25.532318 0-42.553863-17.021545-42.553864-42.553864v-165.960067c4.255386-34.043091-8.510773-59.575409-29.787704-80.852341-12.766159-12.766159-17.021545-29.787704-8.510773-42.553864 4.255386-17.021545 21.276932-25.532318 34.043091-29.787704 123.406204-12.766159 238.301635-55.320023 238.301635-255.323181 0-46.80925-17.021545-93.6185-51.064636-131.916976-12.766159-12.766159-12.766159-29.787704-8.510772-42.553864 12.766159-34.043091 12.766159-68.086182 4.255386-102.129272-21.276932 4.255386-55.320023 17.021545-110.640045 55.320022-8.510773 8.510773-21.276932 8.510773-34.043091 4.255387-89.363113-25.532318-187.236999-25.532318-276.600112 0-12.766159 4.255386-25.532318 4.255386-38.298477-4.255387C307.741455 104.836549 269.442978 92.07039 248.166047 87.815004c-8.510773 34.043091-8.510773 68.086182 4.255386 102.129272 4.255386 17.021545 4.255386 34.043091-8.510773 42.553864-34.043091 38.298477-51.064636 85.107727-51.064636 131.916976 0 200.003158 114.895431 242.557022 238.301635 255.323181 17.021545 0 29.787704 12.766159 34.043091 29.787704 4.255386 17.021545 0 34.043091-8.510773 42.553864-21.276932 21.276932-29.787704 46.80925-29.787704 76.596954v165.960068c0 25.532318-17.021545 42.553863-42.553863 42.553863s-42.553863-17.021545-42.553864-42.553863v-72.341568c-127.66159 21.276932-182.981613-51.064636-221.28009-97.873886-17.021545-21.276932-29.787704-38.298477-46.80925-42.553864-21.276932-4.255386-38.298477-29.787704-29.787704-51.064636 4.255386-21.276932 29.787704-38.298477 51.064636-29.787704 42.553863 12.766159 68.086182 42.553863 93.6185 72.341568 34.043091 46.80925 63.830795 80.852341 153.193908 63.830795v-4.255386c0-25.532318 4.255386-55.320023 12.766159-76.596955-119.150818-25.532318-246.812408-102.129272-246.812408-327.664748 0-63.830795 21.276932-123.406204 59.575409-170.215454-17.021545-59.575409-12.766159-114.895431 12.766159-170.215454 4.255386-12.766159 12.766159-21.276932 25.532318-25.532318 17.021545-4.255386 72.341568-12.766159 187.236999 59.575409 93.6185-21.276932 191.492386-21.276932 280.855499 0 110.640045-72.341568 170.215454-63.830795 187.236999-59.575409 12.766159 4.255386 21.276932 12.766159 25.532319 25.532318 21.276932 55.320023 25.532318 110.640045 12.766159 165.960067 38.298477 46.80925 59.575409 106.384659 59.575408 170.215454 0 242.557022-144.683136 306.387817-246.812408 331.920135 8.510773 25.532318 12.766159 55.320023 12.766159 80.852341V938.892273c0 25.532318-17.021545 42.553863-42.553863 42.553864z"
                        p-id="2598"
                        fill="#ffffff"
                      ></path>
                    </svg>
                  </p>
                </a>
              </li>
              <li className="size-[1.875rem] lg:size-8">
                <a
                  href="https://t.me/minchen3943"
                  target="_blank"
                  rel="noopener noreferrer"
                  title="Telegram"
                >
                  <p className="flex size-[1.875rem] items-center justify-center rounded-full bg-[#2399d5] lg:size-8">
                    <svg
                      className="size-5"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="9801"
                    >
                      <path
                        d="M417.28 795.733333l11.946667-180.48 327.68-295.253333c14.506667-13.226667-2.986667-19.626667-22.186667-8.106667L330.24 567.466667 155.306667 512c-37.546667-10.666667-37.973333-36.693333 8.533333-55.466667l681.386667-262.826666c31.146667-14.08 61.013333 7.68 49.066666 55.466666l-116.053333 546.56c-8.106667 38.826667-31.573333 48.213333-64 30.293334L537.6 695.466667l-84.906667 82.346666c-9.813333 9.813333-17.92 17.92-35.413333 17.92z"
                        fill="#ffffff"
                        p-id="9802"
                      ></path>
                    </svg>
                  </p>
                </a>
              </li>
              <li className="size-[1.875rem] lg:size-8">
                <a
                  href="https://x.com/minchen3943"
                  target="_blank"
                  rel="noopener noreferrer"
                  title="Twitter"
                >
                  <p className="flex size-[1.875rem] items-center justify-center rounded-full bg-black lg:size-8">
                    <svg
                      className="size-[0.875rem] lg:size-4"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="8778"
                      width="200"
                      height="200"
                    >
                      <path
                        d="M776.106667 93.866667h141.141333l-308.352 352.426666 362.752 479.573334h-284.032l-222.464-290.858667L210.602667 925.866667h-141.226667l329.813333-376.96L51.2 93.866667h291.242667l201.088 265.856L776.106667 93.866667z m-49.536 747.52h78.208L299.946667 173.909333H216.021333L726.570667 841.386667z"
                        p-id="8779"
                        fill="#ffffff"
                      ></path>
                    </svg>
                  </p>
                </a>
              </li>
              <li className="size-[1.875rem] lg:size-8">
                <a
                  href="mailto:i@mochenwu.com"
                  target="_blank"
                  rel="noopener noreferrer"
                  title="Email"
                >
                  <p className="flex size-[1.875rem] items-center justify-center rounded-full bg-orange-700 text-white lg:size-8">
                    <FontAwesomeIcon
                      icon={faEnvelope}
                      className="size-5 pl-[0.35px]"
                    />
                  </p>
                </a>
              </li>
            </ul>
          </div>
          <div className="flex items-center justify-center pt-1 lg:p-0">
            <span className="px-3">|</span>
            <span className="pt-1 text-lg font-normal">To 沫鸯</span>
            <ul className="inline-flex gap-3 pl-3">
              <li className="size-[1.875rem] lg:size-8">
                <a
                  href="https://t.me/moyang0709"
                  target="_blank"
                  rel="noopener noreferrer"
                  title="Telegram"
                >
                  <p className="flex size-[1.875rem] items-center justify-center rounded-full bg-[#2399d5] lg:size-8">
                    <svg
                      className="size-5"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="9801"
                    >
                      <path
                        d="M417.28 795.733333l11.946667-180.48 327.68-295.253333c14.506667-13.226667-2.986667-19.626667-22.186667-8.106667L330.24 567.466667 155.306667 512c-37.546667-10.666667-37.973333-36.693333 8.533333-55.466667l681.386667-262.826666c31.146667-14.08 61.013333 7.68 49.066666 55.466666l-116.053333 546.56c-8.106667 38.826667-31.573333 48.213333-64 30.293334L537.6 695.466667l-84.906667 82.346666c-9.813333 9.813333-17.92 17.92-35.413333 17.92z"
                        fill="#ffffff"
                        p-id="9802"
                      ></path>
                    </svg>
                  </p>
                </a>
              </li>
              <li className="size-[1.875rem] lg:size-8">
                <a
                  href="https://x.com/Moyang0709"
                  target="_blank"
                  rel="noopener noreferrer"
                  title="Twitter"
                >
                  <p className="flex size-[1.875rem] items-center justify-center rounded-full bg-black lg:size-8">
                    <svg
                      className="size-[0.875rem] lg:size-4"
                      viewBox="0 0 1024 1024"
                      version="1.1"
                      xmlns="http://www.w3.org/2000/svg"
                      p-id="8778"
                      width="200"
                      height="200"
                    >
                      <path
                        d="M776.106667 93.866667h141.141333l-308.352 352.426666 362.752 479.573334h-284.032l-222.464-290.858667L210.602667 925.866667h-141.226667l329.813333-376.96L51.2 93.866667h291.242667l201.088 265.856L776.106667 93.866667z m-49.536 747.52h78.208L299.946667 173.909333H216.021333L726.570667 841.386667z"
                        p-id="8779"
                        fill="#ffffff"
                      ></path>
                    </svg>
                  </p>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div className="my-3 flex h-fit w-full justify-center font-sans text-sm text-[#626262] lg:mt-2 lg:justify-normal lg:px-44">
        <span className="flex">
          &copy; 2025&ensp;
          <a
            href="https://x.com/minchen3943"
            target="_blank"
            rel="noopener noreferrer"
            title="瞑尘"
            className="underline decoration-[#bcbcbc] decoration-1 underline-offset-4 transition duration-300 ease-in-out hover:text-black hover:decoration-black"
          >
            瞑尘
          </a>
          <span className="items-center px-1 opacity-50">|</span>
          Powered by&ensp;
          <a
            href="https://nextjs.org/"
            target="_blank"
            rel="noopener noreferrer"
            title="Next.js"
            className="underline decoration-[#bcbcbc] decoration-1 underline-offset-4 transition duration-300 ease-in-out hover:text-black hover:decoration-black"
          >
            Next.js
          </a>
          <span className="px-1">&</span>
          <a
            href="https://spring.io/quickstart"
            target="_blank"
            rel="noopener noreferrer"
            title="SpringBoot"
            className="underline decoration-[#bcbcbc] decoration-1 underline-offset-4 transition duration-300 ease-in-out hover:text-black hover:decoration-black"
          >
            SpringBoot
          </a>
        </span>
      </div>
    </div>
  );
}
