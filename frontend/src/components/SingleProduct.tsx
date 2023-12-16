import { useCallback, useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getProduct, removeProduct } from "../http";
import { AuthContext } from "../App";
import { Link } from "react-router-dom";

export default function SingleProduct() {
  const [product, setProduct] = useState<any>({});
  const { token } = useContext(AuthContext);
  const params = useParams();
  useEffect(() => {
    getProduct(token, params?.id).then(setProduct);
  }, [token, params]);
  const navigate = useNavigate();
  const deleteProduct = useCallback(async () => {
    if (!params.id) return;
    if (
      window.confirm(
        "Are you sure you want to delete this product along with its bid proposals and all auctions?"
      )
    ) {
      await removeProduct(token, params.id);
      navigate("/products");
    }
  }, [params, token, navigate]);

  return (
    <div className="space-y-10">
      <div className="flex space-x-10">
        <Link to={`/products/edit/${product.id}`}>
          <button className="text-violet-600 rounded">Edit Product</button>
        </Link>
        <button
          type="button"
          onClick={deleteProduct}
          className="text-red-600 rounded"
        >
          Delete Product
        </button>
      </div>
      <h1 className="text-3xl font-bold">{product.name}</h1>
      <div className="w-full h-60 rounded-md bg-gray-200"></div>
      <p>{product.description}</p>
      <Link
        to={"/products/publish/" + product.id}
        className="inline-block border border-violet-600 rounded px-10 py-3 text-violet-600 hover:bg-violet-600 hover:text-white transition-colors"
      >
        Create Bid From Product &raquo;
      </Link>
    </div>
  );
}
