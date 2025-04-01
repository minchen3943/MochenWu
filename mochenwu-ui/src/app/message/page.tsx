import MessageForm from "@/components/MessageForm";

export default function page() {
  return (
    <div className="w-[54vw] mx-[23vw] mt-[7.5vh]">
      <div className="text-3xl font-semibold">留言板</div>
      <div className="mt-[3vh] text-lg font-normal">来都来了，说点什么吧~</div>
      <div className="mt-[30vh]">
        <MessageForm />
      </div>
    </div>
  );
}
