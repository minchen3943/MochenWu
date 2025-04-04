export default function CopyrightNotice() {
  return (
    <div className="p-8">
      <p>&copy;版权声明：</p>
      <p>作者:&ensp;沫尘屋</p>
      <p>
        本站文章均采用
        <a
          href="https://creativecommons.org/licenses/by-nc-nd/4.0/deed"
          className="px-1 underline underline-offset-4 decoration-1 decoration-[#bcbcbc] hover:decoration-black hover:text-black transition duration-300 ease-in-out">
          署名—非商业性使用—禁止演绎 4.0 协议
        </a>
        ，转载请注明出处。
      </p>
      <p>
        若您发现本站内容有侵犯到您的合法权益，请及时联系
        <a
          href="mailto:minchen3943@outlook.com"
          className="px-1 underline underline-offset-4 decoration-1 decoration-[#bcbcbc] hover:decoration-black hover:text-black transition duration-300 ease-in-out">
          瞑尘
        </a>
      </p>
    </div>
  );
}
