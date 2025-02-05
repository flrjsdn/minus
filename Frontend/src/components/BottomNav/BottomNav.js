import React from "react";
import {faMap, faThumbsUp, faBowlFood } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"; 
import {Link, useLocation} from "react-router-dom";
import "./BottomNav.css";


const BottomNav = () => {
    // 현재 선택된 아이콘을 관리하는 state
    const locationNow = useLocation();
  
      return (
        <nav className="nav-wrapper"> {/* 하단 네비게이션 최상위 태그 */}
          
        {/* 네비게이션을 구성하고 있는 하나의 버튼 */}
          <Link to="/mypage/admin" className="nav-link">
            <div>
              <FontAwesomeIcon
                icon={faMap}
                className={
                  locationNow.pathname === "/mypage/admin"
                    ? "nav-item active-nav-item"
                    : "nav-item"
                }
              />
            </div>
          </Link>

          <Link to="/signup" className="nav-link">
            <div>
              <FontAwesomeIcon
                icon={faThumbsUp}
                className={
                  locationNow.pathname === "/signup"
                    ? "nav-item active-nav-item"
                    : "nav-item"
                }
              />
            </div>
          </Link>

          <Link to="/mypage/user" className="nav-link">
            <div>
              <FontAwesomeIcon
                icon={faBowlFood}
                className={
                  locationNow.pathname === "/mypage/user"
                    ? "nav-item active-nav-item"
                    : "nav-item"
                }
              />
            </div>
          </Link>

        </nav>
      );
    } 
  
  export default BottomNav;