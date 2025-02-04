import { faBell, faRightToBracket } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import "./HeaderContainer.css";
import axios from 'axios';

function HeaderContainer() {
    const locationNow = useLocation();
    let navigate = useNavigate();

    const userId = sessionStorage.getItem("email"); // 로그인 상태 확인

    const sendLoginRequest = async () => {
        try {
            // 이메일, 비밀번호 없이 단순히 로그인 요청 보내기
            const response = await axios.get('http://i12a506.p.ssafy.io:8000/api/users/login');
            
            // 로그인 성공 후 처리
            console.log('로그인 요청 성공:', response.data);
            // 응답 데이터에 따른 추가 작업
        } catch (error) {
            console.error('로그인 요청 실패:', error);
        }
    };

    const handleConnectUser = () => {
        if (userId === null) {
            // 카카오 로그인 대신 axios 요청 보내기
            sendLoginRequest(); // 로그인 요청 보내기
        } else {
            navigate("/"); // 로그인된 경우 홈으로 이동
        }
    };

    const handleConnectBell = () => {
        if (userId === null) {
            // 카카오 로그인 대신 axios 요청 보내기
            sendLoginRequest();
        } else {
            navigate("/notifications");
        }
    };

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

                {/* 로그인 버튼 누를시 axios 로그인 요청 */}
                <div className="link" onClick={handleConnectUser}>
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
