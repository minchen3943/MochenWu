import MessageForm from "@/components/MessageForm";
import MessageList from "@/components/MessageList";
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "留言板",
  description: "沫尘屋的留言板~",
  openGraph: {
    description: "沫尘屋的留言板~",
  },
  twitter: {
    description: "沫尘屋的留言板~",
  },
};
export default function Page() {
  return (
    <div className="mx-[5vw] mt-[5vh] w-[90vw] lg:mx-[23vw] lg:mt-[7.5vh] lg:w-[54vw]">
      <div className="text-3xl font-semibold">留言板</div>
      <div className="mt-[3vh] text-lg font-normal">来都来了，说点什么吧~</div>
      <div className="mt-[35vh] lg:mt-[30vh]">
        <MessageForm />
      </div>
      <div className="mt-20 lg:mt-40">
        <MessageList />
      </div>
    </div>
  );
}
