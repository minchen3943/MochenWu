import { Metadata } from "next";
import type { Viewport } from "next";
import localFont from "next/font/local";
import "./globals.css";
import Navbar from "@/components/Navbar";
import { ViewTransitions } from "next-view-transitions";

const Font = localFont({
  src: "../public/font.woff",
});

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
        <body className={Font.className}>
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
