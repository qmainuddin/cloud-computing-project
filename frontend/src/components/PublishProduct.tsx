import { useCallback, useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getProduct, publishProduct } from "../http";
import { AuthContext } from "../App";
import { useForm } from "react-hook-form";

export default function PublishProduct() {
  const params = useParams();
  const { token } = useContext(AuthContext);
  const [product, setProduct] = useState<{
    name: string;
    description: string;
    categories: [];
  }>({ name: "", description: "", categories: [] });
  const {
    register,
    handleSubmit,
    formState: { isValid, isSubmitting },
  } = useForm({ mode: "onChange" });

  const navigate = useNavigate();

  useEffect(() => {
    if (!params.id) return;
    getProduct(token, params.id).then(setProduct);
  }, [params, token]);

  const onSubmit = useCallback(
    (data: any) => {
      if (!params.id) return;
      publishProduct(token, params.id, data).then((bid) =>
        navigate("/products/bid-proposal/" + bid.id)
      );
    },
    [token, params.id, navigate]
  );

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-5 max-w-5xl p-5">
      <h1 className="text-2xl font-bold">Publish Product : {product.name}</h1>
      <div className="space-y-2">
        <label className="font-semibold">Starting Price</label>
        <input
          type="number"
          placeholder="Starting Price"
          {...register("startingPrice")}
          className="p-3 rounded border border-gray-400 w-full"
        />
      </div>
      <div className="space-y-2">
        <label className="font-semibold">Deposit</label>
        <input
          type="number"
          placeholder="Deposit"
          {...register("deposit")}
          className="p-3 rounded border border-gray-400 w-full"
        />
      </div>
      <div className="space-y-2">
        <label className="font-semibold">Payment Due Date</label>
        <input
          type="date"
          placeholder="Payment Due Date"
          min={new Date().toISOString().split("T")[0]}
          {...register("paymentDueDate", {
            min: new Date().toISOString().split("T")[0],
          })}
          className="p-3 rounded border border-gray-400 w-full"
        />
      </div>
      <button
        type="submit"
        disabled={!isValid || isSubmitting}
        className="bg-violet-600 text-white p-3 rounded disabled:opacity-50"
      >
        Create Bid
      </button>
    </form>
  );
}
