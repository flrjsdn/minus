import React from "react";
import "./SearchBar.css";
import { useNavigate } from "react-router-dom";

function SearchBar() {

    const navigate = useNavigate();

    const handleFocus = () => {
        navigate("/search");
    };

    return (
        <div className="search-bar">
            <div className="search-bar-input-container">
                <input
                    type="text"
                    className="search-input"
                    placeholder="원하는 제품을 검색해 보세요!"
                    onFocus={handleFocus}
                />
            </div>
        </div>
    );
}

export default SearchBar;
