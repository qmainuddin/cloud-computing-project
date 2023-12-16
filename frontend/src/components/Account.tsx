import { useCallback, useContext, useEffect } from "react";
import { useForm } from "react-hook-form";
import { getMe, updateUser } from "../http";
import { AuthContext } from "../App";
import { Link } from "react-router-dom";

export default function Account() {
  const { register, handleSubmit, setValue } = useForm({ mode: "onChange" });
  const { token } = useContext(AuthContext);
  const onSubmit = useCallback(
    async (data: any) => {
      const user = await updateUser(token, data);
      for (const key in user) {
        setValue(key, user[key]);
      }
      alert("Your user details have been stored");
      window.location.reload();
    },
    [token, setValue]
  );

  useEffect(() => {
    getMe(token).then((user) => {
      for (const key in user) {
        setValue(key, user[key]);
      }
    });
  }, [token, setValue]);

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-5 max-w-5xl">
      <h1 className="text-3xl font-bold">Account Detail</h1>
      <div className="space-y-2">
        <label className="font-semibold">Name</label>
        <input
          type="text"
          placeholder="Name"
          {...register("name")}
          className="p-3 rounded border border-gray-400 w-full"
        />
      </div>
      <div className="space-y-2">
        <label className="font-semibold">Email</label>
        <input
          type="email"
          placeholder="Email"
          {...register("email")}
          className="p-3 rounded border border-gray-400 w-full"
        />
      </div>
      <div className="space-y-2">
        <label className="font-semibold">Password</label>
        <input
          type="password"
          placeholder="Password"
          {...register("password")}
          className="p-3 rounded border border-gray-400 w-full"
        />
      </div>
      <div className="space-y-2">
        <label className="font-semibold">Balance</label>
        <input
          type="number"
          placeholder="Balance"
          {...register("balance")}
          className="p-3 rounded border border-gray-400 w-full"
        />
        {/* <p className="text-xs">
          Your balance won't show for your security, but you can udpate it from
          here.
        </p> */}
      </div>

      <div className="pt-5">
        <button type="submit" className="bg-violet-600 text-white p-3 rounded">
          Save Account Info
        </button>
      </div>
    </form>
  );
}
