import { faBell, faRightToBracket, faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import "./HeaderContainer.css";
import useAuth from "../../hooks/useAuth";

function HeaderContainer() {
    const locationNow = useLocation();
    const navigate = useNavigate();
    const {logindata} = useAuth(); // 로그인된 사용자 정보 가져오기

    const handleConnectBell = () => {
        if (!logindata) {
            navigate("/map");
        } else {
            navigate("/notifications");
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
        </header>
    );
}

export default HeaderContainer;
