import { useForm } from "react-hook-form";
import { createProduct, getCategories } from "../http";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../App";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

export default function CreateProduct() {
  const [categories, setCategories] = useState([]);
  const { token } = useContext(AuthContext);
  const navigate = useNavigate();
  const { register, handleSubmit } = useForm({ mode: "onChange" });

  useEffect(() => {
    getCategories(token).then(setCategories);
  }, [token]);

  const onSubmit = async (data: any) => {
    const product = await createProduct(token, data);
    navigate("/products/" + product.id);
  };
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
        <div className="space-y-2">
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
          Create Product
        </button>
      </form>
    </div>
  );
}
