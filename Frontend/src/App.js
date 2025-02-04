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
import SearchPage from "./pages/SearchPage";
import StoreDetail from "./pages/SearchPage/StoreDetail"
import MakeCoupons from "./pages/MakeCoupons";
import Flea from "./pages/Flea";
import Stock from "./pages/Stock";
import Notice from "./pages/Notice";

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
        <Route path="/mypage/admin/flea" element={<Flea/>}/>
        <Route path="/mypage/admin/stock" element={<Stock/>}/>
        <Route path="/mypage/admin/coupon" element={<MakeCoupons/>}/>
        <Route path="/mypage/admin/notice" element={<Notice/>}/>


      <Route path="/oauth/kakao" element={<KakaoRedirect />} />

      <Route path="/kiosk" element={<Kiosk/>}/>
      <Route path="/kmain" element={<KioskMainScreen/>}/>
      <Route path="/search" element={<SearchPage/>}/>
      <Route path="/search/1" element={<StoreDetail/>}/>
      {/* <Route path="/callback" component={KakaoLoginHandler}/> */}

      {/*/!* 보호된 라우트 *!/*/}
      {/*<Route*/}
      {/*    path="/kiosk/{user_no}"*/}
      {/*    element={*/}
      {/*      <LoginRequired user={user}>*/}
      {/*        <AdminRequired user={user} requiredRole="A">*/}
      {/*          <Kiosk />*/}
      {/*        </AdminRequired>*/}
      {/*      </LoginRequired>*/}
      {/*    }*/}
      {/*/>*/}


    </Routes>

    </div>
  );
}

export default App;
