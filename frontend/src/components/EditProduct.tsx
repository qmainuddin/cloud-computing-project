import { useForm } from "react-hook-form";
import { saveProduct, getCategories, getProduct } from "../http";
import { useCallback, useContext, useEffect, useState } from "react";
import { AuthContext } from "../App";
import { useNavigate, useParams } from "react-router-dom";
import { Link } from "react-router-dom";

export default function EditProduct() {
  const [categories, setCategories] = useState([]);
  const { token } = useContext(AuthContext);
  const navigate = useNavigate();
  // const [product, setProduct] = useState<{
  //   name: string;
  //   description: string;
  //   categories: [];
  // }>({ name: "", description: "", categories: [] });
  const params = useParams();
  const { register, handleSubmit, setValue } = useForm({
    mode: "onChange",
  });

  useEffect(() => {
    getProduct(token, params.id).then((product) => {
      setValue("name", product.name);
      setValue("description", product.description);
      setValue(
        "categories",
        product.categories.map((c: any) => c.id + "")
      );
    });
    getCategories(token).then(setCategories);
  }, [token, params, setValue]);

  const onSubmit = useCallback(
    async (data: any) => {
      if (!params.id) return;
      const product = await saveProduct(token, params.id, data);
      navigate("/products/" + product.id);
    },
    [token, params, navigate]
  );

  return (
    <div className="space-y-10">
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-5 max-w-5xl">
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
          <label className="font-semibold">Description</label>
          <textarea
            {...register("description")}
            placeholder="Description"
            rows={5}
            className="p-3 rounded border border-gray-400 w-full resize-none"
          />
        </div>
        <div className="space-y-5">
          <label className="font-semibold">Category</label>
          <div className="grid grid-cols-4 gap-4">
            {categories.map((category: any, i) => (
              <label className="flex items-center space-x-3">
                <input
                  value={category.id}
                  type="checkbox"
                  {...register("categories")}
                  className="w-5 h-5 rounded-md"
                />
                <span>{category.name}</span>
              </label>
            ))}
          </div>
          {/* <select
            {...register("categories")}
            placeholder="Description"
            multiple
            className="p-3 rounded border border-gray-400 w-full resize-none"
          >
            {categories.map((category: any, i) => (
              <option key={`category-${i}`} value={category.id}>
                {category.name}
              </option>
            ))}
          </select> */}
        </div>
        <button type="submit" className="bg-violet-600 text-white p-3 rounded">
          Save Product
        </button>
      </form>
    </div>
  );
}
