import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import "./style.css";

const SearchBar = ({ coords }) => {
    const [query, setQuery] = useState(""); // 사용자 입력 값

    const navigate = useNavigate();
    const location = useLocation();

    // 뒤로가기 버튼 클릭 시 홈으로 이동
    const handleBack = () => {
        navigate("/");
    };

    // 검색 버튼 클릭 시 검색 페이지로 이동
    const handleSearchClick = () => {
        if (coords?.lat && coords?.lng) {
            navigate("/search", { state: { coords } });
        } else {
            console.error("좌표 정보가 유효하지 않습니다.");
        }
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
                value={query}
                onChange={(e) => setQuery(e.target.value)} // 실시간으로 query 상태 업데이트
                onClick={handleSearchClick} // 검색 클릭 시 호출
            />
        </div>
    );
};

export default SearchBar;
