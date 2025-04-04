"use client";
import { Link } from "next-view-transitions";
import { usePathname } from "next/navigation";
import { useEffect, useState } from "react";

export default function Navbar() {
  const pathname = usePathname();
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      const scrollThreshold = window.innerHeight / 5;
      setIsScrolled(window.scrollY > scrollThreshold);
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  const linkList = [
    { name: "首页", link: "/" },
    { name: "博客", link: "/blog" },
    { name: "留言", link: "/message" },
    { name: "关于", link: "/about" },
  ];

  return (
    <div
      className={`py-[1.5vh] transition-all duration-300 ${
        isScrolled
          ? "border-[#00000000] bg-[#f8edff81] drop-shadow-lg backdrop-blur-3xl"
          : ""
      }`}
    >
      <div
        className={`left-0 right-0 mx-auto grid h-[4.875vh] w-fit items-center rounded-full px-3 transition-all duration-300 lg:h-[3.75vh] ${
          isScrolled
            ? ""
            : "border border-[#5b5b5b35] bg-[#f8edff81] drop-shadow-lg"
        }`}
      >
        <div className="flex">
          {linkList.map((item) => (
            <Link
              key={item.link}
              href={item.link}
              className={`grid w-[4.125rem] justify-center rounded-lg text-xl ${
                pathname === item.link
                  ? "text-[#9A73B5]"
                  : "text-[#4A4A4A] lg:hover:text-[#D6C6E1]"
              }`}
            >
              <span className="mt-[0.125rem]">{item.name}</span>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
}
