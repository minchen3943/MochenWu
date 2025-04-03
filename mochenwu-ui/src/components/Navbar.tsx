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
      className={`transition-all duration-300 py-[1.5vh] ${
        isScrolled
          ? "bg-[#f8edff81] backdrop-blur-3xl drop-shadow-lg border-[#00000000]"
          : ""
      }`}>
      <div
        className={`grid w-fit lg:h-[3.75vh] h-[4.875vh] mx-auto left-0 right-0 px-3 items-center rounded-full transition-all duration-300 ${
          isScrolled
            ? ""
            : "drop-shadow-lg border border-[#5b5b5b35] bg-[#f8edff81]"
        }`}>
        <div className="flex">
          {linkList.map((item) => (
            <Link
              key={item.link}
              href={item.link}
              className={`grid justify-center text-xl rounded-lg w-[4.125rem] ${
                pathname === item.link
                  ? "text-[#9A73B5]"
                  : "text-[#4A4A4A] lg:hover:text-[#D6C6E1]"
              }`}>
              <span className="mt-[0.125rem]">{item.name}</span>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
}
