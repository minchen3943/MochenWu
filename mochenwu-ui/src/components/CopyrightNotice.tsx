export default function CopyrightNotice() {
  return (
    <div className="p-8">
      <p>&copy;版权声明：</p>
      <p>作者:&ensp;沫尘屋</p>
      <p>
        本站文章均采用
        <a
          href="https://creativecommons.org/licenses/by-nc-nd/4.0/deed"
          target="_blank"
          rel="noopener noreferrer"
          className="px-1 underline decoration-[#bcbcbc] decoration-1 underline-offset-4 transition duration-300 ease-in-out hover:text-black hover:decoration-black"
        >
          署名—非商业性使用—禁止演绎 4.0 协议
        </a>
        ，转载请注明出处。
      </p>
      <p>
        若您发现本站内容有侵犯到您的合法权益，请及时联系
        <a
          href="mailto:minchen3943@outlook.com"
          target="_blank"
          rel="noopener noreferrer"
          className="px-1 underline decoration-[#bcbcbc] decoration-1 underline-offset-4 transition duration-300 ease-in-out hover:text-black hover:decoration-black"
        >
          瞑尘
        </a>
      </p>
    </div>
  );
}
