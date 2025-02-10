import React, { useState } from 'react';
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import SearchBar from "../../components/SearchBar";
import KakaoMapBackground from "../../components/KakaoMapBackground";
import DraggableBottomSheet from "../../components/DraggableBottomSheet/DraggableBottomSheet";
import BottomNav from "../../components/BottomNav/BottomNav";
import KakaoPostcodePopup from "../../components/KakaoPostcodePopup";
import useGeolocation from "../../hooks/UseGeolocation";
import './style.css';

const MainPage = () => {
    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const { address, coords, error, refreshLocation } = useGeolocation();
    const [addressData, setAddressData] = useState({
        fullAddress: '서울 강남구 테헤란로 212',
        zoneCode: '06220'
    });

    // 주소 검색 처리
    const handleAddressComplete = async (data) => {
        setAddressData(data);

        // Kakao Geocoder를 사용해 좌표 변환
        try {
            const response = await fetch(
                `https://dapi.kakao.com/v2/local/search/address.json?query=${data.fullAddress}`,
                {
                    headers: { Authorization: `KakaoAK ${process.env.REACT_APP_KAKAO_REST_API_KEY}` },
                }
            );

            const result = await response.json();
            if (result.documents.length > 0) {
                const { x: lng, y: lat } = result.documents[0];

                // 새 좌표로 위치 업데이트
                refreshLocation(lat, lng);
            } else {
                console.error("주소를 좌표로 변환하는 데 실패했습니다.");
            }
        } catch (err) {
            console.error("좌표 변환 중 오류 발생:", err);
        }
    };

    // 에러 발생 시 기본 주소 사용
    if (error) console.error(error);

    return (
        <div className="mainpagecontents">
            <HeaderContainer />
            <div className="mainpagecurrentaddress">
                <div className="mainpageaddressdetail">
                    현재 주소: {address || addressData.fullAddress}
                </div>
                <button onClick={() => setIsPopupOpen(true)} className="addressSearchButton">
                    주소 찾기
                </button>
            </div>
            <SearchBar coords={coords} />
            <DraggableBottomSheet coords={coords} />
            <BottomNav />
            <KakaoMapBackground
                address={address || addressData.fullAddress}
                coords={coords}
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
