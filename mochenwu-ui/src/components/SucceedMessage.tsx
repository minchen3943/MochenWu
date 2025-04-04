"use client";

import { useEffect, useState } from "react";
import { Transition } from "@headlessui/react";
import { CheckCircleIcon } from "@heroicons/react/24/outline";
import { XMarkIcon } from "@heroicons/react/20/solid";

interface SucceedMessageProps {
  message1: string;
  message2: string;
}

const SucceedMessage: React.FC<SucceedMessageProps> = ({
  message1,
  message2,
}) => {
  const [show, setShow] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setShow(false);
    }, 10000);
    return () => clearTimeout(timer);
  }, []);
  return (
    <>
      <div
        aria-live="assertive"
        className="z-100 pointer-events-none fixed inset-0 flex px-4 py-6"
      >
        <div className="flex w-full flex-col items-end space-y-4">
          <Transition show={show}>
            <div className="data-closed:opacity-0 data-enter:transform data-enter:duration-300 data-enter:ease-out data-closed:data-enter:translate-y-2 data-leave:duration-100 data-leave:ease-in sm:data-closed:data-enter:translate-x-2 sm:data-closed:data-enter:translate-y-0 pointer-events-auto w-full max-w-sm overflow-hidden rounded-lg bg-white shadow-lg ring-1 ring-black/5 transition">
              <div className="p-4">
                <div className="flex items-start">
                  <div className="shrink-0">
                    <CheckCircleIcon
                      aria-hidden="true"
                      className="size-6 text-green-400"
                    />
                  </div>
                  <div className="ml-3 w-0 flex-1 pt-0.5">
                    <p className="text-sm font-medium text-gray-900">
                      {message1}
                    </p>
                    <p className="mt-1 text-sm text-gray-500">{message2}</p>
                  </div>
                  <div className="ml-4 flex shrink-0">
                    <button
                      type="button"
                      onClick={() => {
                        setShow(false);
                      }}
                      className="inline-flex rounded-md bg-white text-gray-400 hover:text-gray-500"
                    >
                      <span className="sr-only">Close</span>
                      <XMarkIcon aria-hidden="true" className="size-5" />
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </Transition>
        </div>
      </div>
    </>
  );
};
export default SucceedMessage;
