import BlogList from "@/components/BlogList";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "博客列表",
  description: "沫尘屋的博客列表~",
  openGraph: {
    description: "沫尘屋的博客列表~",
  },
  twitter: {
    description: "沫尘屋的博客列表~",
  },
};

export default async function page() {
  return (
    <>
      <div className="mx-[5vw] mt-5 w-auto text-2xl font-medium lg:mx-[20vw]">
        <BlogList />
      </div>
    </>
  );
}
