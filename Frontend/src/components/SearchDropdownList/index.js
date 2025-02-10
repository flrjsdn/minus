import React from "react";
import "./style.css";

const SearchDropdownList = ({ results, onItemClick }) => {
    if (!results || results.length === 0) return null;

    return (
        <ul className="dropdown">
            {results.map((item, index) => (
                <li key={index} onClick={() => onItemClick(item)}>
                    {item.itemName}
                </li>
            ))}
        </ul>
    );
};

export default SearchDropdownList;
