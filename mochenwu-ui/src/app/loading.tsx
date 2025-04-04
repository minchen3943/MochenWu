import LoaderAnimal from "@/components/LoaderAnimal";

export default function Loading() {
  return (
    <div className="h-[94vh] w-full">
      <div className="grid place-items-center px-6 py-32 lg:px-8 lg:py-64">
        <span className="mb-10 text-4xl">加载中.....</span>
        <LoaderAnimal />
      </div>
    </div>
  );
}
