import "./App.css";
import React from "react";

import { Route, Routes } from "react-router-dom";
import MainPage from "./pages/MainPage";
import Kiosk from "./pages/Kiosk"
import KioskMainScreen from "./pages/Kiosk/main";
import SignUp from "./pages/SignUpPage/SignUp";
import OwnerSignUp from "./pages/SignUpPage/OwnerSignUp";
import UserSignUp from "./pages/SignUpPage/UserSignUp";
import MyPage from "./pages/MyPage";
import Coupons from "./pages/Coupons";
import Chats from "./pages/Chats";
import KakaoRedirect from "./pages/KakaoRedirect";
import SearchPage from "./pages/Search";
import StoreDetail from "./pages/StoreDetail"
import MakeCoupons from "./pages/MakeCoupons";
import Notice from "./pages/Notice";
import Map from "./pages/Map";
import StockRequests from "./pages/StockRequests";
import FleaRequests from "./pages/FleaRequests";

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
      <Route path="/mypage/user" element={<MyPage/>}/>
        <Route path="/mypage/user/coupons" element={<Coupons/>}/>
        <Route path="/mypage/user/chats" element={<Chats/>}/>
      <Route path="/mypage/admin" element={<MyPage/>}/>
        <Route path="/mypage/admin/flea" element={<FleaRequests/>}/>
        <Route path="/mypage/admin/stock" element={<StockRequests/>}/>
        <Route path="/mypage/admin/coupon" element={<MakeCoupons/>}/>
        <Route path="/mypage/admin/notice" element={<Notice/>}/>
      <Route path="/map" element={<Map/>}/>

      <Route path="/oauth/kakao" element={<KakaoRedirect />} />

      <Route path="/kiosk" element={<Kiosk/>}/>
      <Route path="/kmain" element={<KioskMainScreen/>}/>
      <Route path="/search" element={<SearchPage/>}/>
      <Route path="/storedetail/1" element={<StoreDetail/>}/>

    </Routes>

    </div>
  );
}

export default App;
