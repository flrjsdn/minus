import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import SearchBar from "../../components/SearchBar";
import SearchDropdownList from "../../components/SearchDropdownList";
import SearchNavbar from "../../components/SearchNavbar";
import './style.css';

const Search = () => {
    const location = useLocation();

    // 전달받은 좌표 또는 기본값 설정
    const coords = location.state?.coords || { latitude: 37.5012767, longitude: 127.0396002 };
    const [results, setResults] = useState([]); // API 결과 상태
    const [isDropdownVisible, setDropdownVisible] = useState(false); // 드롭다운 표시 여부

    const handleItemClick = (item) => {
        console.log("선택된 항목:", item);
        console.log(coords);
        setDropdownVisible(false); // 선택 후 드롭다운 숨김
    };

    return (
        <div className="searchpage">
            {/*<HeaderContainer />*/}
            {/* 검색바에 상태 전달 */}
            <SearchBar
                setResults={setResults}
                setDropdownVisible={setDropdownVisible}
            />
            {/* 드롭다운 표시 */}
            {isDropdownVisible && (
                <SearchDropdownList
                    results={results}
                    onItemClick={handleItemClick}
                />
            )}
            <SearchNavbar />
        </div>
    );
};

export default Search;
