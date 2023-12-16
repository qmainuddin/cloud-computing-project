import { useContext, useEffect, useState } from "react";
import { getProducts } from "../http";
import { AuthContext } from "../App";
import _unionBy from "lodash/unionBy";
import _orderBy from "lodash/orderBy";
import { Link } from "react-router-dom";

export default function Products() {
  const [loading, setLoading] = useState(true);
  const { token, setToken } = useContext(AuthContext);
  const [page, setPage] = useState(0);
  const [doneLoading, setDoneLoading] = useState(false);
  const [products, setProducts] = useState([]);

  useEffect(() => {
    setLoading(true);
    getProducts(token, page)
      .then((ps) => {
        if (!ps || !ps.length) setDoneLoading(true);
        setProducts((pr) => _orderBy(_unionBy(ps, pr, "id"), "id", "desc"));
      })
      .finally(() => setLoading(false));
  }, [token, page]);

  return (
    <div className="space-y-10">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">All Products</h1>
        <Link to="/products/create">
          <button className="text-violet-600 rounded">Create Product</button>
        </Link>
      </div>
      {loading && products.length === 0 ? (
        <div className="grid grid-cols-4 gap-10">
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
            <div className="grid grid-cols-4 gap-10">
              {products.map((product: any, i) => (
                <Link
                  key={i}
                  to={`/products/${product.id}`}
                  className="space-y-3"
                >
                  <div className="bg-gray-200 rounded-md w-full h-44"></div>
                  <p className="font-bold">{product.name}</p>
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
