import BlogList from "@/components/BlogList";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "沫尘屋-博客",
  openGraph: {
    title: "沫尘屋-博客",
  },
};

export default async function page() {
  return (
    <>
      <div className="mx-[5vw] mt-5 w-[90vw] text-2xl font-medium lg:mx-[20vw] lg:w-[60vw]">
        <BlogList />
      </div>
    </>
  );
}
