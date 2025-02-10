import { faBell, faRightToBracket, faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import useAuth from "../../hooks/useAuth"; // useAuth 훅 가져오기
import "./HeaderContainer.css";

function HeaderContainer() {
    const locationNow = useLocation();
    const navigate = useNavigate();
    const { logindata } = useAuth(); //로그인 정보 가져오기
    const [showDropdown, setShowDropdown] = useState(false);

    const handleConnectBell = () => {
        setShowDropdown(!showDropdown);
    };

    // userType에 따라 마이페이지 이동 경로 설정
    const handleNavigateToMyPage = () => {
        if (logindata?.userType === "A") {
            navigate("/mypage/admin");
        } else if (logindata?.userType === "U") {
            navigate("/mypage/user");
        }
    };

    return (
        <header className="header">
            <Link to="/" className="link">
                <img src="/logo.png" alt="Muin Logo" className="logo" />
            </Link>

            <div className="icons">
                <div className="link" onClick={handleConnectBell}>
                    <div className="bell">
                        <FontAwesomeIcon
                            icon={faBell}
                            className={locationNow.pathname === "/notifications" ? "bell-icon active-bell-icon" : "bell"}
                        />
                    </div>
                </div>

                <div className="login">
                    {logindata ? (
                        // 로그인된 유저: faUser 아이콘 + 클릭 시 마이페이지 이동
                        <FontAwesomeIcon icon={faUser} className="login-icon" onClick={handleNavigateToMyPage} />
                    ) : (
                        // 로그인되지 않은 유저: faRightToBracket 아이콘 + 로그인 페이지 이동
                        <Link to="http://i12a506.p.ssafy.io:8000/api/users/login">
                            <FontAwesomeIcon icon={faRightToBracket} className="login-icon" />
                        </Link>
                    )}
                </div>
            </div>

            {/* 드롭다운 메뉴 */}
            {showDropdown && (
                <div className="dropdown">
                    <p className="welcome-text" onClick={handleNavigateToMyPage}>
                        {logindata ? `${logindata.userName}님 환영합니다.` : "환영합니다."}
                    </p>
                    <p className="mypage-link" onClick={handleNavigateToMyPage}>
                        마이페이지
                    </p>
                    <Link to="/mypage/coupon">쿠폰함</Link>
                    <Link to="/mypage/chat">채팅목록</Link>
                    <button className="logout">로그아웃</button>
                </div>
            )}
        </header>
    );
}

export default HeaderContainer;
