import { EnvelopeIcon, LockClosedIcon } from "@heroicons/react/24/solid";
import { useCallback, useContext, useState } from "react";
import { useForm } from "react-hook-form";
import { login, signUp } from "../http";
import { AuthContext } from "../App";

const emailRegex =
  /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

export default function Home() {
  const { setToken } = useContext(AuthContext);
  const [showSignUp, setShowSignUp] = useState(false);
  const {
    register,
    handleSubmit,
    watch,
    formState: { isValid, isSubmitting, errors },
  } = useForm({ mode: "onChange" });
  const noSubmit = useCallback(
    async (data: any) => {
      //TODO: proceed to sign up or sign in
      try {
        if (showSignUp) {
          await signUp(data.email, data.password, data.licenseNumber);
          setShowSignUp(false);
        } else {
          const token = await login(data);
          token && setToken(token);
        }
      } catch (ex) {
        alert(ex);
      }
    },
    [setToken, showSignUp]
  );

  return (
    <div className="flex justify-center items-center h-screen">
      <form
        onSubmit={handleSubmit(noSubmit)}
        className="space-y-5 w-full max-w-sm mx-auto"
      >
        <h1 className="text-4xl font-bold">Bidding System</h1>
        <div className="space-y-2">
          <div className="relative">
            <input
              type="email"
              required
              {...register("email", {
                required: { value: true, message: "Email is required" },
                pattern: { value: emailRegex, message: "Invalid email" },
              })}
              className="border-2 rounded border-gray-300 p-3 pl-10 w-full"
              placeholder="Enter your email"
            />
            <EnvelopeIcon className="w-5 h-5 text-zinc-400 absolute top-1/2 -translate-y-1/2 left-3" />
          </div>
          {errors.email && (
            <p className="text-xs text-red-500">
              {errors.email.message?.toString()}
            </p>
          )}
        </div>
        <div className="space-y-2">
          <div className="relative">
            <input
              type="password"
              {...register("password", {
                required: { value: true, message: "Password is required" },
                min: {
                  value: 8,
                  message: "password must be a minimium of 8 characters",
                },
                pattern: {
                  value: /(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}/,
                  message:
                    "password must contain at least one uppercase letter, one lowercase letter, and one number",
                },
              })}
              className="border-2 rounded border-gray-300 p-3 pl-10 w-full"
              placeholder="Password"
            />
            <LockClosedIcon className="w-5 h-5 text-zinc-400 absolute top-1/2 -translate-y-1/2 left-3" />
          </div>
          {errors.password && (
            <p className="text-xs text-red-500">
              {errors.password.message?.toString()}
            </p>
          )}
        </div>
        {showSignUp && (
          <>
            <div className="space-y-2">
              <div className="relative">
                <input
                  type="password"
                  {...register("passwordConfirmation", {
                    required: {
                      value: true,
                      message: "Password confirmation is required",
                    },
                    validate: (val: string) => {
                      if (watch("password") !== val)
                        return "Passwords do not match";
                    },
                  })}
                  className="border-2 rounded border-gray-300 p-3 pl-10 w-full"
                  placeholder="Password"
                />
                <LockClosedIcon className="w-5 h-5 text-zinc-400 absolute top-1/2 -translate-y-1/2 left-3" />
              </div>

              {errors.passwordConfirmation && (
                <p className="text-xs text-red-500">
                  {errors.passwordConfirmation.message?.toString()}
                </p>
              )}
            </div>

            <div className="space-y-2">
              <div className="relative">
                <input
                  type="text"
                  {...register("licenseNumber", {
                    required: {
                      value: true,
                      message: "License number is required",
                    },
                    min: { value: 8, message: "Invalid license number" },
                  })}
                  className="border-2 rounded border-gray-300 p-3 pl-10 w-full"
                  placeholder="License Number"
                />
              </div>

              {errors.licenseNumber && (
                <p className="text-xs text-red-500">
                  {errors.licenseNumber.message?.toString()}
                </p>
              )}
            </div>
          </>
        )}
        <button
          type="submit"
          disabled={!isValid || isSubmitting}
          className="bg-violet-600 text-white rounded-md p-3 w-full disabled:opacity-50"
        >
          {showSignUp ? "Sign up" : "Sign in"}
        </button>
        <button
          type="button"
          onClick={() => setShowSignUp((s) => !s)}
          className="text-violet-600"
        >
          {showSignUp ? "Sign in instead" : "Sign up instead"}
        </button>
      </form>
    </div>
  );
}
