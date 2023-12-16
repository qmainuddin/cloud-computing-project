import "./App.css";
import { RouterProvider } from "react-router-dom";
import { createContext, useEffect, useState } from "react";
import router from "./router";

import Home from "./components/Home";
import { getMe } from "./http";

type AuthContextType = {
  token: string;
  setToken: (token: string) => void;
  authUser: User | null;
};

type User = {
  id: "";
  email: string;
  name: "";
  balance: number;
};

export const AuthContext = createContext<AuthContextType>({
  token: "",
  setToken: () => {},
  authUser: null,
});

function App() {
  const [authUser, setAuthUser] = useState<User | null>(null);
  const [token, setToken] = useState(localStorage.getItem("token") as string);
  useEffect(() => {
    localStorage.getItem("token") &&
      setToken(localStorage.getItem("token") as string);
  }, []);

  useEffect(() => {
    localStorage.setItem("token", token);
    if (token) {
      getMe(token).then(setAuthUser);
    }
  }, [token]);

  return (
    <AuthContext.Provider value={{ token, setToken, authUser }}>
      {!token ? <Home /> : <RouterProvider router={router} />}
    </AuthContext.Provider>
  );
}

export default App;
