import CopyrightNotice from "@/components/CopyrightNotice";
import Markdown from "@/components/Markdown";
import axios from "axios";
import config from "../../../../mcw-config.json";
import { Metadata } from "next";

export async function generateMetadata({
  params,
}: {
  params: Promise<{ slug: string }>;
}): Promise<Metadata> {
  const { slug } = await params;
  const blogData = await axios
    .get(
      `${config.server.axios.protocol}://${config.server.axios.host}/api/article/get/id?articleId=${slug}`,
    )
    .then((res) => res.data.data);
  return {
    title: blogData.articleTitle,
    description: `沫尘屋的博客-${blogData.articleTitle}`,
    openGraph: {
      description: `沫尘屋的博客-${blogData.articleTitle}`,
    },
    twitter: {
      description: `沫尘屋的博客-${blogData.articleTitle}`,
    },
  };
}

export default async function Page({
  params,
}: {
  params: Promise<{ slug: string }>;
}) {
  const { slug } = await params;

  return (
    <>
      <div className="bg-linear-to-b absolute top-[-0vh] -z-50 h-[40vh] w-full from-[#ead3ff8a] to-[#00000000]"></div>
      <div className="mx-[5vw] w-[90vw] lg:mx-[20vw] lg:w-[60vw]">
        <Markdown articleId={Number(slug)} />
        <CopyrightNotice />
      </div>
    </>
  );
}
