import { createBrowserRouter } from "react-router-dom";
import Products from "./components/Products";
import Account from "./components/Account";
import SingleProduct from "./components/SingleProduct";
import CreateProduct from "./components/CreateProduct";
import Layout from "./components/Layout";
import EditProduct from "./components/EditProduct";
import PublishProduct from "./components/PublishProduct";
import SingleBid from "./components/SingleBid";
import BidList from "./components/BidList";
import MyBids from "./components/MyBids";
import MyAuctions from "./components/MyAuctions";

export default createBrowserRouter([
  {
    path: "",
    element: <Layout />,
    children: [
      {
        path: "",
        element: <BidList />,
      },
      { path: "account", element: <Account /> },
      { path: "bids", element: <MyBids /> },
      { path: "auctions", element: <MyAuctions /> },
      {
        path: "products",
        children: [
          { path: "", element: <Products /> }, // TODO: disable this router later
          { path: ":id", element: <SingleProduct /> },
          { path: "create", element: <CreateProduct /> },
          { path: "edit/:id", element: <EditProduct /> },
          { path: "publish/:id", element: <PublishProduct /> },
          { path: "bid-proposal/:id", element: <SingleBid /> },
        ],
      },
      { path: "accounts", element: <Account /> },
    ],
  },
]);
