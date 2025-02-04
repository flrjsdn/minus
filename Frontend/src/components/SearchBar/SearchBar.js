import React from "react";
import "./SearchBar.css";

function SearchBar() {
    return (
        <div className="search-bar">
            <div className="search-bar-input-container">
                <input
                    type="text"
                    className="search-input"
                    placeholder="원하는 제품을 검색해 보세요!"
                />
            </div>
        </div>
    );
}

export default SearchBar;
