import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./style.css";

const SearchBar = ({ coords, setQuery }) => {
    const [localQuery, setLocalQuery] = useState(""); // 로컬 상태로 입력값 관리
    const navigate = useNavigate();
    const location = useLocation();

    // 뒤로가기 버튼 클릭 시 이전 페이지로 이동
    const handleBack = () => {
        navigate(-1);
    };

    const handleSearch = () => {
        navigate(`search?lat=${coords.lat}&lng=${coords.lng}`);
    }

    // 검색어 입력 처리
    const handleInputChange = (e) => {
        const value = e.target.value;
        setLocalQuery(value);
        setQuery(value); // 부모 컴포넌트에 검색어 전달
    };

    return (
        <div className="searchbar">
            {/* /search 경로일 때만 뒤로가기 버튼 표시 */}
            {location.pathname === "/search" && (
                <>
                    <button className="back-button" onClick={handleBack}>
                        뒤로가기
                    </button>
                    <input
                        type="text"
                        className="search-input"
                        placeholder="검색어를 입력하세요"
                        value={localQuery}
                        onChange={handleInputChange}
                    />
                </>
            )}
            {location.pathname !== "/search" && (
                <input
                    type="text"
                    className="search-input"
                    placeholder="검색어를 입력하세요"
                    value={localQuery}
                    onClick={handleSearch}
                />
            )}
        </div>

    );
};

export default SearchBar;
