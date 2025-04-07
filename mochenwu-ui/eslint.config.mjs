import { dirname } from "path";
import { fileURLToPath } from "url";
import { FlatCompat } from "@eslint/eslintrc";
import js from "@eslint/js";

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

const compat = new FlatCompat({
  baseDirectory: __dirname,
  recommendedConfig: js.configs.recommended,
});

const eslintConfig = [
  js.configs.recommended,
  ...compat.extends(
    "plugin:@typescript-eslint/recommended",
    "next/core-web-vitals",
    "next/typescript",
  ),

  {
    languageOptions: {
      parserOptions: {
        ecmaFeatures: { jsx: true },
        ecmaVersion: "latest",
        sourceType: "module",
      },
    },
    rules: {
      "@next/next/no-img-element": "off",
      "react-hooks/rules-of-hooks": "off",
      "@typescript-eslint/no-explicit-any": "off",
      "react-hooks/exhaustive-deps": "warn",
      "no-console": "warn",
      eqeqeq: "error",
    },
  },

  {
    files: ["src/components/**/*.tsx"],
    rules: {
      "react/prop-types": "off",
    },
  },
];

export default eslintConfig;
