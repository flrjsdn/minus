import React, { useState } from "react";
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import SearchBar from "../../components/SearchBar";
import KakaoMapBackground from "../../components/KakaoMapBackground";
import KakaoPostcodePopup from "../../components/KakaoPostcodePopup";
import DraggableBottomSheet from "../../components/DraggableBottomSheet/DraggableBottomSheet";
import BottomNav from "../../components/BottomNav/BottomNav";
import useGeolocation from "../../hooks/UseGeolocation";

import "./style.css";

const MainPage = () => {
    const { coords: initialCoords } = useGeolocation();
    const [coords, setCoords] = useState(initialCoords);
    const [address, setAddress] = useState("");
    const [isPopupOpen, setIsPopupOpen] = useState(false);

    // 카카오 우편번호 검색 완료 핸들러
    const handleAddressComplete = async (data) => {
        setAddress(data.fullAddress); // 한글 주소 저장
        setCoords(null); // 지도에서 새 좌표를 받아올 수 있도록 초기화
    };

    // 지도에서 역지오코딩 결과 업데이트 핸들러
    const handleAddressUpdate = (newAddress) => {
        setAddress(newAddress);
    };

    return (
        <div className="mainpagecontents">
            <HeaderContainer />
            <div className="mainpagecurrentaddress">
                <div className="mainpageaddressdetail">
                    현재 주소: {address}
                </div>
                <button onClick={() => setIsPopupOpen(true)} className="addressSearchButton">
                    주소 찾기
                </button>
            </div>
            <SearchBar coords={coords} />
            <DraggableBottomSheet coords={coords} />
            <BottomNav />
            <KakaoMapBackground
                address={address}
                coords={coords}
                onAddressUpdate={handleAddressUpdate}
            />
            {isPopupOpen && (
                <KakaoPostcodePopup
                    onClose={() => setIsPopupOpen(false)}
                    onComplete={handleAddressComplete}
                />
            )}
        </div>
    );
};

export default MainPage;
