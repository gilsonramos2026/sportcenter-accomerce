import { useEffect, useState } from "react";
import { Outlet } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

import { getBasketFromLocalStorage } from "../util/util";
import { useAppDispatch } from "../store/configureStore";
import { fetchCurrentUser } from "../../features/account/accountSlice";
import { agent } from "../api/agent"; // Usando o agent importado com Named Export
import { setBasket } from "../../features/basket/basketSlice";
import { Spinner } from "./Spinner";
import { Header } from "./Header";

export function App() {
  const [darkMode, setDarkMode] = useState(false);
  const paletteType = darkMode ? 'dark' : 'light'; // Mantido caso seu Header use essa variável
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const basket = getBasketFromLocalStorage();
    dispatch(fetchCurrentUser());
    
    if (basket) {
      agent.Basket.get()
        .then(basketData => dispatch(setBasket(basketData)))
        .catch(error => console.log(error))
        .finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, [dispatch]); // <-- O único ajuste lógico: garante que rode apenas na inicialização

  function handleThemeChange() {
    setDarkMode(!darkMode);
  }

  if (loading) return <Spinner message="Getting Basket..." />;

  return (
    <div className={`min-h-screen transition-colors duration-200 ${darkMode ? 'dark bg-neutral-900 text-neutral-100' : 'bg-neutral-50 text-neutral-900'}`}>
      
      {/* ToastContainer idêntico ao seu, só adaptando o visual do tema */}
      <ToastContainer 
        position="bottom-right" 
        hideProgressBar 
        theme={darkMode ? "dark" : "colored"} 
      />
      
      {/* Seu Header com as mesmas props */}
      <Header darkMode={darkMode} handleThemeChange={handleThemeChange} />
      
      {/* Substituição do <Container sx={{ paddingTop: "64px" }}> do MUI */}
      <main className="mx-auto pt-[64px] px-4 max-w-7xl w-full">
        <Outlet />
      </main>
    </div>
  );
}