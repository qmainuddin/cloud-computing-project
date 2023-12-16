import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../App";
import { getClosedBids, getMyBids } from "../http";
import _orderBy from "lodash/orderBy";
import _unionBy from "lodash/unionBy";
import { Link } from "react-router-dom";

export default function MyBids() {
  const [loading, setLoading] = useState(true);
  const { token, setToken, authUser } = useContext(AuthContext);
  const [page, setPage] = useState(0);
  const [doneLoading, setDoneLoading] = useState(false);
  const [bids, setBids] = useState([]);
  const [closedBids, setClosedBids] = useState([]);

  useEffect(() => {
    setLoading(true);
    getMyBids(token, page)
      .then((ps) => {
        if (!ps || !ps.length) setDoneLoading(true);
        setBids((pr) => _orderBy(_unionBy(ps, pr, "id"), "id", "desc"));
      })
      .catch((err) => setToken(""))
      .finally(() => setLoading(false));

    getClosedBids(token).then(setClosedBids);
  }, [token, page, setToken]);

  return (
    <div className="space-y-10">
      <>
        <div className="flex justify-between items-center">
          <h1 className="text-3xl font-bold">Closed Bids</h1>
        </div>

        {
          <div className="grid grid-cols-2 sm:grid-cols-4 gap-10">
            {closedBids.map((bid: any, i) => (
              <Link
                key={i}
                to={`/products/bid-proposal/${bid.id}`}
                className="space-y-3"
              >
                <div className="bg-gray-200 rounded-md w-full h-44"></div>
                <p className="font-bold">{bid.product.name}</p>
              </Link>
            ))}
          </div>
        }
      </>

      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">My Bids</h1>
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
                  to={`/products/bid-proposal/${bid.id}`}
                  className="space-y-3"
                >
                  <div
                    className={`bg-gray-200 rounded-md w-full h-44 ${
                      bid.published ? "border-2 border-teal-600" : ""
                    }`}
                  ></div>
                  <p className={`font-bold`}>{bid.product.name}</p>
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
