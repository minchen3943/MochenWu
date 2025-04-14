import type { Metadata } from "next";
import type { Viewport } from "next";
import localFont from "next/font/local";
import "./globals.css";
import Navbar from "@/components/Navbar";
import { ViewTransitions } from "next-view-transitions";
import Footer from "@/components/Footer";
import Script from "next/script";

const Font = localFont({
  src: "../../public/font.woff2",
});

export const metadata: Metadata = {
  title: {
    template: "%s - 沫尘屋",
    default: "沫尘屋",
  },
  applicationName: "沫尘屋",
  description: "沫鸯和瞑尘一起制作的个人网站",
  keywords: [
    "沫尘屋",
    "博客",
    "生活",
    "学习",
    "个人网站",
    "Next.js",
    "React",
    "JavaScript",
  ],
  publisher: "沫尘屋",
  authors: [
    { name: "沫鸯", url: "https://mochenwu.com" },
    { name: "瞑尘", url: "https://mochenwu.com" },
  ],
  openGraph: {
    title: "沫尘屋",
    description: "沫鸯和瞑尘一起制作的个人网站",
    url: "https://mochenwu.com",
    siteName: "沫尘屋",
    locale: "zh_CN",
    type: "website",
  },
  twitter: {
    card: "summary_large_image",
    site: "@minchen3943",
    title: "沫尘屋",
    description: "沫鸯和瞑尘一起制作的个人网站",
  },
  robots: "index, follow",
};

export const viewport: Viewport = {
  width: "device-width",
  initialScale: 1.0,
  maximumScale: 1.0,
  userScalable: false,
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <ViewTransitions>
      <Script id="info">
        {`(function(){
  console.info(
    "%c 沫尘屋 V1.0.0 %c https://mochenwu.com",
    "color:#FFFFFF;margin:0.75rem 0;padding:5px 0;background:#F9C0E4",
    "margin:0.75rem 0;padding:5px 0;background:#F0F0F0"
  );
  document.prepend(document.createComment(\`Powered By Nextjs, SpringBoot\`))
}())`}
      </Script>
      <html lang="zh-CN">
        <body className={Font.className}>
          <div id="root">
            <header>
              <Navbar />
            </header>
            <main>{children}</main>
            <footer>
              <Footer />
            </footer>
          </div>
        </body>
      </html>
    </ViewTransitions>
  );
}
