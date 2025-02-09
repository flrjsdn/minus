import React from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./SearchBar.css";

function SearchBar() {
    const navigate = useNavigate();
    const location = useLocation();

    // 뒤로가기 버튼 클릭 시 홈으로 이동
    const handleBack = () => {
        navigate("/");
    };

    return (
        <div className="searchbar">
            {/* /search 경로일 때만 뒤로가기 버튼 표시 */}
            {location.pathname === "/search" && (
                <button className="back-button" onClick={handleBack}>
                    뒤로가기
                </button>
            )}
            <input
                type="text"
                className="search-input"
                placeholder="검색어를 입력하세요"
                onClick={() => navigate("/search")} // 클릭 시 /search로 이동
            />
        </div>
    );
}

export default SearchBar;
