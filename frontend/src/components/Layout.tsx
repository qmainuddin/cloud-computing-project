import { useContext } from "react";
import { Link } from "react-router-dom";
import { Outlet } from "react-router-dom";
import { AuthContext } from "../App";

export default function Layout() {
  const { authUser, setToken } = useContext(AuthContext);
  return (
    <div className="max-w-7xl py-10 space-y-10 mx-auto">
      <div className="flex items-center justify-between">
        <div className="flex items-center space-x-10 font-semibold py-5">
          <Link to="/">
            <button className="text-violet-600 rounded">Home</button>
          </Link>

          <Link to="/products">
            <button className="text-violet-600">My Products</button>
          </Link>

          <Link to="/bids">
            <button className="text-violet-600">My Bids</button>
          </Link>

          <Link to="/auctions">
            <button className="text-violet-600">My Auctions</button>
          </Link>
        </div>

        <div className="flex items-center space-x-5">
          <Link
            to="/account"
            className="space-x-3 inline-flex items-center outline-none"
          >
            <button className="text-violet-600 rounded">
              {authUser?.email}
            </button>
            <strong className="px-5 py-2 flex items-center justify-center text-xs bg-orange-300 rounded-md">
              {authUser?.balance}
            </strong>
          </Link>

          <div>
            <button
              onClick={() => {
                setToken("");
              }}
              className="text-red-500 rounded"
            >
              Logout
            </button>
          </div>
        </div>
      </div>
      <Outlet />
    </div>
  );
}
