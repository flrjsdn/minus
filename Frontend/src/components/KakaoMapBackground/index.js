import React, { useEffect } from "react";
import styled from "styled-components";

const apiKey = process.env.REACT_APP_KAKAO_JS_API_KEY;

const KakaoMapBackground = ({ address, coords }) => {
    useEffect(() => {
        if (!coords || !address) return;

        // Kakao Maps SDK 스크립트를 동적으로 로드
        const script = document.createElement("script");
        script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${apiKey}&libraries=services&autoload=false`;
        script.async = true;

        script.onload = () => {
            if (window.kakao && window.kakao.maps) {
                window.kakao.maps.load(() => {
                    const kakao = window.kakao;

                    // 지도 컨테이너와 옵션 설정
                    const mapContainer = document.getElementById("map"); // 지도를 표시할 div
                    const mapOption = {
                        center: new kakao.maps.LatLng(coords.lat, coords.lng), // 좌표를 중심으로 설정
                        level: 3, // 확대 레벨
                        draggable: true, // 지도 드래그 가능 여부 설정
                        zoomable: true, // 지도 확대/축소 가능 여부 설정
                    };

                    // 지도 생성
                    const map = new kakao.maps.Map(mapContainer, mapOption);

                    // 마커 추가
                    const marker = new kakao.maps.Marker({
                        position: new kakao.maps.LatLng(coords.lat, coords.lng),
                        map,
                    });

                    // 줌 컨트롤 추가
                    const zoomControl = new kakao.maps.ZoomControl();
                    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
                });
            }
        };

        script.onerror = () => {
            console.error("Kakao Maps SDK 스크립트를 불러오는 데 실패했습니다.");
        };

        document.head.appendChild(script);

        return () => {
            document.head.removeChild(script);
        };
    }, [coords]); // coords가 변경될 때마다 지도 업데이트

    return (
        <div
            id="map"
            style={{
                width: "100vw",
                height: "100vh",
                zIndex: "1",
                position: "fixed",
                top: "0",
            }}
        ></div>
    );
};

const MapContainer = styled.div`
    position: relative;
    width: 100%;
    height: 100%;
`;

const MapDiv = styled.div`
    width: 100%;
    height: 100%;
`;

const SearchBox = styled.input`
    position: absolute;
    top: 10px;
    left: 10px;
    width: 200px;
    padding: 8px;
    font-size: 14px;
    border: 1px solid #ddd;
    border-radius: 5px;
    outline: none;
    background: white;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    z-index: 1000;
`;

export default KakaoMapBackground;
