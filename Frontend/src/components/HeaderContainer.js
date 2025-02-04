import {faBell, faRightToBracket } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import {Link, useLocation, useNavigate} from "react-router-dom";
import "./HeaderContainer.css";

function HeaderContainer() {
    const locationNow = useLocation();
    let navigate = useNavigate();

    const userId = sessionStorage.getItem("email");

    const redirectToKakaoLogin = () => {

        const REST_API_KEY = process.env.REACT_APP_KAKAO_REST_API_KEY; // 카카오 REST API 키
        const REDIRECT_URI = process.env.REACT_APP_KAKAO_REDIRECT_URI; // 리디렉션 URI
    
        const link = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;
    
        window.location.href = link; // 카카오 로그인 페이지로 이동
    };


    const handleConnectUser = () => {
        if (userId === null) {
            redirectToKakaoLogin();
        } else {
            navigate("/");
        }
    };

    const handleConnectBell = () => {
        if (userId === null) {
            redirectToKakaoLogin();
        } else {
            navigate("/notifications");
        }
    };

    // const handleConnectBell = () => {
    // // 알림아이콘 클릭 시 로그인 상태 체크 후 라우팅 진행.
    // if (userId === null) {
    //     navigate("/login");
    // } else if (userId !== null) {
    //     navigate("/notifications");
    // }
    // };

    // const handleConnectUser = () => {
    //     // 유저버튼 클릭 시 로그인 상태 체크 후 라우팅 진행.
    //     if (userId === null) {
    //       navigate("/login");
    //     } else if (userId !== null) {
    //       navigate("/");
    //     }
    //   };

    //   const handleConnectUser = () => {
    //     // 유저버튼 클릭 시 로그인 상태 체크 후 카카오 로그인 URL로 라우팅 진행.
    //     if (userId === null) {
    //       const REST_API_KEY = "038237efe192df92320c3eebb41ea4d5";  // 카카오 REST API 키
    //       const REDIRECT_URI = "http://localhost:3001/callback";  // 카카오 로그인 후 리디렉션할 URI
      
    //       const link = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;
      
    //       // 카카오 로그인 URL로 리디렉션
    //       window.location.href = link;
    //     } else if (userId !== null) {
    //       navigate("/");  // 유저가 로그인 상태라면 메인 페이지로 이동
    //     }
    //   };
      
    return (
        <header className="header">

            {/* 무인 로고  */}
            <Link to="/" className="link">
                <img src="/logo.png" alt="Muin Logo" className="logo" />
            </Link>

            <div className="icons">

                {/* 알림 버튼 누를시 아이콘 변경 */}
                <div to="/notifications" className="link" onClick={handleConnectBell}>
                    <div className="bell">
                        <FontAwesomeIcon
                            icon={faBell}
                            className={
                                locationNow.pathname === "/notifications" ? "bell-icon active-bell-icon" : "bell"                          
                            } 
                            />
                    </div>
                </div>
                
                {/* 로그인인 버튼 누를시 아이콘 변경 */}
                <div to="/mypage" className="link" onClick={handleConnectUser}>
                    <div className="login">
                        <FontAwesomeIcon
                            icon={faRightToBracket}
                            className={
                                locationNow.pathname === "/login" ? "login-icon active-login-icon" : "login"                          
                            } 
                        />
                    </div>
                </div>
            </div>  
        </header>
    ); 
}

export default HeaderContainer;