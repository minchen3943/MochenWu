import CopyrightNotice from "@/components/CopyrightNotice";
import Markdown from "@/components/Markdown";

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
      <div></div>
    </>
  );
}
