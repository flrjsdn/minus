import { faBell, faRightToBracket, faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import "./HeaderContainer.css";

function HeaderContainer() {
    const locationNow = useLocation();
    const navigate = useNavigate();
    const [showDropdown, setShowDropdown] = useState(false);
    const [logindata, setLogindata] = useState(null); // 로그인 데이터 상태
    const [loading, setLoading] = useState(true); // 로딩 상태

    // 로그인 정보 가져오기
    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const response = await fetch("http://i12a506.p.ssafy.io:8000/api/users/info", {
                    method: "GET",
                    credentials: "include", // 쿠키 포함 (로그인 상태 유지)
                });

                if (!response.ok) {
                    throw new Error("사용자 정보를 가져오는 데 실패했습니다.");
                }

                const data = await response.json();
                setLogindata(data); // 로그인 정보 저장
            } catch (error) {
                console.error(error);
                setLogindata(null); // 오류 발생 시 로그인 해제 상태로 설정
            } finally {
                setLoading(false); // 로딩 종료
            }
        };

        fetchUserInfo();
    }, []); // 최초 렌더링 시 한 번 실행

    const handleConnectBell = () => {
        setShowDropdown(!showDropdown);
    };

    const handleNavigateToMyPage = () => {
        navigate("/mypage/admin");
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
                    {loading ? (
                        <p>로딩 중...</p>
                    ) : logindata ? (
                        <>
                            <Link to="/mypage/user">
                                <FontAwesomeIcon icon={faUser} className="login-icon" />
                            </Link>
                        </>
                    ) : (
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
                        {logindata ? `${logindata.username}님 환영합니다.` : "환영합니다."}
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
