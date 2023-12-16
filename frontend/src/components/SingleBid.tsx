import { useCallback, useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { AuthContext } from "../App";
import {
  doBid,
  getAuctionsByProposal,
  getBid,
  increaseBid,
  publishTheBid,
  updateBid,
} from "../http";
import { useForm } from "react-hook-form";
import dayjs from "../utils/dayjs";
import {
  CheckCircleIcon,
  ExclamationCircleIcon,
} from "@heroicons/react/24/solid";
import { Link } from "react-router-dom";

export default function SingleBid() {
  const [bid, setBid] = useState<{
    id: string;
    deposit: number;
    startingPrice: number;
    paymentDueDate: string;
    published: boolean;
    product: {
      name: string;
      id: string;
      description: string;
      category: string[];
      owner: { id: string };
    };
  }>({
    id: "",
    deposit: 0,
    startingPrice: 0,
    paymentDueDate: "",
    published: false,
    product: {
      id: "",
      name: "",
      description: "",
      category: [],
      owner: { id: "" },
    },
  });
  const { token, authUser } = useContext(AuthContext);
  const [auction, setAuction] = useState<any>({});
  const params = useParams();
  const {
    register,
    handleSubmit,
    setValue,
    formState: { isValid, isSubmitting },
  } = useForm({ mode: "onChange" });

  useEffect(() => {
    if (!params.id) return;
    getBid(token, params.id).then((bid) => {
      for (let key in bid) {
        if (key === "paymentDueDate") {
          setValue(key, bid[key].split("T")[0]);
        } else setValue(key, bid[key]);
      }
      setBid(bid);
    });

    getAuctionsByProposal(token, params.id).then((auction) => {
      setAuction(auction);
      setValue("bidPrice", auction.bidPrice);
    });
  }, [token, params, setValue]);

  const navigate = useNavigate();

  const onEditBid = useCallback(
    async (data: any) => {
      if (!params?.id) return;
      await updateBid(token, params.id, data);
      alert("Updated the bid");
    },
    [token, params]
  );

  const mine = bid.product.owner.id === authUser?.id;
  const belowDeposit = (authUser?.balance ?? 0) < bid.deposit;

  const onSubmit = useCallback(
    async (data: any) => {
      if (data.bidPrice < bid.startingPrice)
        return alert("Your bid price must be higher than the starting price");

      if (
        !window.confirm(
          `This will take $${bid.deposit} from your account. Are you sure?`
        )
      )
        return;
      if (!params.id) return;
      try {
        console.log(auction);
        if (auction?.id) {
          try {
            const result = await increaseBid(token, auction.id, data);
            if (result) {
              alert("Auction updated");
            }
            window.location.reload();
          } catch (ex) {
            console.log(ex);
            alert(ex);
          }
          return;
        }
        await doBid(token, params.id, data).then((bid) => {});
        alert("You have put your bid for this product");
        navigate("/");
      } catch (ex) {}
    },
    [params, token, navigate, bid, auction]
  );

  const publishBid = useCallback(() => {
    if (!params.id) return;
    publishTheBid(token, params.id).then(setBid);
  }, [token, params]);

  return (
    <div className="space-y-5">
      {belowDeposit && !mine && (
        <div className="bg-red-500 text-white p-3 rounded flex items-center space-x-3">
          <ExclamationCircleIcon className="w-10 h-10" />
          <span className="text-white text-opacity-60">
            You have insufficient balance in your account.{" "}
            <Link
              to="/account"
              className="text-white text-opacity-80 font-semibold"
            >
              Load your balance.
            </Link>
          </span>
        </div>
      )}

      <div className="flex justify-between items-center">
        <div className="flex space-x-5 items-center">
          {mine && <CheckCircleIcon className="w-6 h-6 text-teal-600" />}
          <h1 className="text-2xl font-bold space-x-5">
            <span>Bid of Product</span>
            <span className="text-violet-500">{bid.product.name}</span>
          </h1>
        </div>
        {!bid.published && (
          <button
            onClick={publishBid}
            type="button"
            className="bg-violet-600 text-white rounded p-3"
          >
            Publish
          </button>
        )}
      </div>

      {mine && !bid.published ? (
        <form
          onSubmit={handleSubmit(onEditBid)}
          className="space-y-5 max-w-3xl"
        >
          <div className="space-y-2">
            <label className="font-semibold">Deposit</label>
            <input
              type="number"
              placeholder="Deposit"
              {...register("deposit", { required: true })}
              className="p-3 rounded border border-gray-400 w-full disabled:opacity-70 disabled:bg-gray-200"
            />
          </div>
          <div className="space-y-2">
            <label className="font-semibold">Starting Price</label>
            <input
              type="number"
              placeholder="Starting Price"
              {...register("startingPrice", { required: true })}
              className="p-3 rounded border border-gray-400 w-full disabled:opacity-70 disabled:bg-gray-200"
            />
          </div>
          <div className="space-y-2">
            <label className="font-semibold">Payment Due Date</label>
            <input
              type="date"
              placeholder="Payment Due Date"
              {...register("paymentDueDate", { required: true })}
              className="p-3 rounded border border-gray-400 w-full disabled:opacity-70 disabled:bg-gray-200"
            />
          </div>
          <div className="flex space-x-5">
            <button className="bg-violet-600 text-white rounded p-3">
              Submit
            </button>
          </div>
        </form>
      ) : (
        <>
          <p>
            <strong>Deposit:</strong> ${bid.deposit}
          </p>
          <p>
            <strong>Starting Price:</strong> ${bid.startingPrice}
          </p>
          <p>
            <strong>Payment Due Date: </strong>
            In{" "}
            {dayjs
              .duration(
                dayjs(bid.paymentDueDate).diff(dayjs(), "minute"),
                "minute"
              )
              .humanize()}
          </p>
        </>
      )}

      {!mine && (
        <form onSubmit={handleSubmit(onSubmit)} className="pt-10 space-y-5">
          <div className="space-y-2">
            <label className="font-semibold">My Bid Price</label>
            <input
              type="number"
              disabled={belowDeposit}
              placeholder="My Bid Price"
              {...register("bidPrice", {
                required: true,
                min: bid.startingPrice,
              })}
              className="p-3 rounded border border-gray-400 w-full disabled:opacity-70 disabled:bg-gray-200"
            />
            <p className="text-xs">
              Place a minimum of {bid.startingPrice} to bid for this product
            </p>
          </div>

          <button
            type="submit"
            disabled={!isValid || isSubmitting || belowDeposit}
            className="bg-violet-600 text-white p-3 rounded disabled:opacity-50"
          >
            {auction ? "Increase Your Bid Price" : "Bid for this product"}
          </button>
        </form>
      )}
    </div>
  );
}
