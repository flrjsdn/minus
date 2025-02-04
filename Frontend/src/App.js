import "./App.css";
import React from "react";
// import { useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import MainPage from "./pages/MainPage";
import Kiosk from "./pages/Kiosk"
import KioskMainScreen from "./pages/Kiosk/main";
import SignUp from "./pages/SignUpPage/SignUp";
import OwnerSignUp from "./pages/SignUpPage/OwnerSignUp";
import UserSignUp from "./pages/SignUpPage/UserSignUp";
import MyPage from "./pages/MyPage";
import EditProfile from "./pages/EditProfile";
import Coupons from "./pages/Coupons";
import Chats from "./pages/Chats";
import KakaoRedirect from "./pages/KakaoRedirect";


function App() {

  // function setScreenSize() {
  //   let vh = window.innerHeight * 0.01;
  //   document.documentElement.style.setProperty("--vh", `${vh}px`);
  // }
  // useEffect(() => {
  //   setScreenSize();
  // });

  return (
    <div className="App">

    <Routes>
      <Route path="/" element={<MainPage/>}/>
      <Route path="/signup" element={<SignUp/>}/>
      <Route path="/owner-signup" element={<OwnerSignUp/>}/>
      <Route path="/user-signup" element={<UserSignUp/>}/>
      <Route path="/mypage" element={<MyPage/>}/>
        <Route path="/mypage/edit" element={<EditProfile/>}/>
        <Route path="/mypage/coupons" element={<Coupons/>}/>
        <Route path="/mypage/chats" element={<Chats/>}/>
      <Route path="/oauth/kakao" element={<KakaoRedirect />} />

      <Route path="/kiosk" element={<Kiosk/>}/>
      <Route path="/kmain" element={<KioskMainScreen/>}/>
    </Routes>

    </div>
  );
}

export default App;
