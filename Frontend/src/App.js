import "./App.css";
import React from "react";
import { useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import MainPage from "./pages/MainPage";
import Kiosk from "./pages/Kiosk"
import KioskMainScreen from "./pages/Kiosk/main";
import KakaoLoginHandler from "./components/KakaoLoginHandler";

function App() {

  function setScreenSize() {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }
  useEffect(() => {
    setScreenSize();
  });

  return (
    <div className="App">

    <Routes>
      <Route path="/" element={<MainPage/>}/>
      <Route path="/kiosk" element={<Kiosk/>}/>
      <Route path="/kmain" element={<KioskMainScreen/>}/>
      {/* <Route path="/callback" component={KakaoLoginHandler}/> */}
    </Routes>

    </div>
  );
}

export default App;
