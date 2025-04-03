import Markdown from "@/components/Markdown";

export default async function Page({
  params,
}: {
  params: Promise<{ slug: string }>;
}) {
  const { slug } = await params;

  return (
    <>
      <div className="absolute w-full h-[40vh] top-[-0vh] bg-gradient-to-b from-[#ead3ff8a] to-[#00000000] -z-50"></div>
      <div className="w-[90vw] lg:w-[60vw] mx-[5vw] lg:mx-[20vw]">
        <Markdown articleId={Number(slug)} />
      </div>
    </>
  );
}
