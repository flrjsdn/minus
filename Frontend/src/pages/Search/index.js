import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import SearchBar from "../../components/SearchBar";
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import SearchDropdownList from "../../components/SearchDropdownList";
import SearchNavbar from "../../components/SearchNavbar";
import './style.css';

// 디바운스 함수 정의
const debounce = (func, delay) => {
    let timeoutId;
    return (...args) => {
        if (timeoutId) clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
            func(...args);
        }, delay);
    };
};

const Search = () => {
    const apiUrl = process.env.REACT_APP_BACKEND_API_URL;
    const [query, setQuery] = useState(""); // 검색어 상태
    const [results, setResults] = useState([]); // API 결과 상태
    const [isDropdownVisible, setDropdownVisible] = useState(false); // 드롭다운 표시 여부
    const navigate = useNavigate();


    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);

    const lat = queryParams.get('lat');
    const lng = queryParams.get('lng');

    // 디바운스를 적용한 API 호출 함수
    const fetchResults = debounce(async (searchQuery) => {
        if (searchQuery.trim() === "") {
            setResults([]);
            setDropdownVisible(false);
            return;
        }

        try {
            const response = await axios.get(`${apiUrl}/api/items/autocomplete`, {
                params: { prefix: searchQuery }, // 쿼리 파라미터 전달
            });
            setResults(response.data);// API 결과 업데이트
            console.log(response.data)
            setDropdownVisible(true);
        } catch (error) {
            console.error("검색 오류:", error);
        }
    }, 500); // 500ms 지연

    // 검색어 변경 시 디바운스 호출
    const handleQueryChange = (newQuery) => {
        setQuery(newQuery);
        fetchResults(newQuery);
        console.log(newQuery)
    };

    const handleItemClick = (item) => {
        console.log("선택된 항목:", item);
        setDropdownVisible(false); // 선택 후 드롭다운 숨김
        navigate(`/search/results?lat=${lat}&lng=${lng}&itemId=${item.itemId}`);
    };

    return (
        <div className="searchpage">
            <HeaderContainer/>
            <SearchBar setQuery={handleQueryChange} />
            {isDropdownVisible && results?.length > 0 && (
                <SearchDropdownList results={results} onItemClick={handleItemClick} />
            )}
            <SearchNavbar />
        </div>
    );
};

export default Search;
