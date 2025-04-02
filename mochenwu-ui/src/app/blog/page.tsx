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
      <div className="w-[90vw] lg:w-[60vw] mx-[5vw] mt-5 lg:mx-[20vw] font-medium text-2xl">
        <BlogList />
      </div>
    </>
  );
}
