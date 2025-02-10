import { useLocation } from "react-router-dom";
import './style.css'
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import KakaoMapBackground from "../../components/KakaoMapBackground";
import React from "react";

const SearchResult = () => {

    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);

    const lat = queryParams.get('lat');
    const lng = queryParams.get('lng');

    return (
        <div>
            <HeaderContainer/>
            <h1>검색 결과</h1>
            {/*<KakaoMapBackground coords={lat: lat, lng: lng} />*/}

        </div>
    )
}

export default SearchResult;