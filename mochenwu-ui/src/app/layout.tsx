import { Metadata } from "next";
import type { Viewport } from "next";
import { Noto_Sans_SC } from "next/font/google";
import "./globals.css";
import Navbar from "@/components/Navbar";
import { ViewTransitions } from "next-view-transitions";
import Script from "next/script";

const notoSans = Noto_Sans_SC({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "沫尘屋",
  robots: "noindex, nofollow",
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
      <html lang="zh-CN">
        <body className={notoSans.className}>
          <div id="root">
            <header>
              <Navbar />
            </header>
            <main>{children}</main>
          </div>
        </body>
      </html>
    </ViewTransitions>
  );
}
