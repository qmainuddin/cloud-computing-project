import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../App";
import _orderBy from "lodash/orderBy";
import _unionBy from "lodash/unionBy";
import { Link } from "react-router-dom";
import { getAuctions } from "../http";
import _range from "lodash/range";

export default function MyAuctions() {
  const [loading, setLoading] = useState(true);
  const { token, setToken } = useContext(AuthContext);
  const [page, setPage] = useState(0);
  const [doneLoading, setDoneLoading] = useState(false);
  const [bids, setBids] = useState([]);

  const [year, setYear] = useState(null);

  useEffect(() => {
    setLoading(true);
    if (year) setBids([]);
    getAuctions(token, page, year)
      .then((ps) => {
        if (!ps || !ps.length) setDoneLoading(true);
        setBids((pr) => _orderBy(_unionBy(ps, pr, "id"), "id", "desc"));
      })
      .catch((err) => setToken(""))
      .finally(() => setLoading(false));
  }, [token, page, setToken, year]);

  return (
    <div className="space-y-10">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">All Auctions</h1>
      </div>
      <div className="flex space-x-3 items-center">
        <p>Filter by Year</p>
        <div className="border rounded border-gray-300 p-3">
          <select
            className="outline-none border-none bg-transparent"
            onChange={(e: any) => setYear(e.target.value)}
          >
            <option value="">Reset</option>
            {_range(2000, new Date().getFullYear() + 1).map((y, i) => (
              <option defaultChecked={y == year} key={`year-${i}`} value={y}>
                {y}
              </option>
            ))}
          </select>
        </div>
      </div>
      {loading && bids.length === 0 ? (
        <div className="grid grid-cols-2 sm:grid-cols-4 gap-10">
          {Array(10)
            .fill(0)
            .map((_, i) => (
              <div
                key={`loading-${i}`}
                className="w-full h-44 rounded-md bg-gray-100 animate-pulse"
              />
            ))}
        </div>
      ) : (
        <>
          {
            <div className="grid grid-cols-2 sm:grid-cols-4 gap-10">
              {bids.map((bid: any, i) => (
                <Link
                  key={i}
                  to={`/products/bid-proposal/${bid.bidProposal.id}`}
                  className="space-y-3"
                >
                  <div className="bg-gray-200 rounded-md w-full h-44"></div>
                  <div className="space-y-1">
                    <p className="font-bold">{bid.bidProposal.product.name}</p>
                    <p className="text-gray-500">${bid.bidPrice.toFixed(2)}</p>
                  </div>
                </Link>
              ))}
            </div>
          }
          <div className="pb-10">
            <button
              type="button"
              disabled={doneLoading || loading}
              className="bg-violet-600 text-white rounded py-3 px-10 disabled:opacity-50"
              onClick={() => setPage((p) => p + 1)}
            >
              {doneLoading ? "That's all we have" : "Load more"}
            </button>
          </div>
        </>
      )}
    </div>
  );
}
