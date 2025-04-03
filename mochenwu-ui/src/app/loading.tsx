import LoaderAnimal from "@/components/LoaderAnimal";

export default function Loading() {
  return (
    <div className="w-full h-[94vh]">
      <div className="grid place-items-center px-6 py-32 lg:py-64 lg:px-8">
        <span className="text-4xl mb-10">加载中.....</span>
        <LoaderAnimal />
      </div>
    </div>
  );
}
