import React, { useEffect } from "react";
import styled from "styled-components";

const apiKey = process.env.REACT_APP_KAKAO_JS_API_KEY;

const KakaoMapBackground = ({ onLocationSelect }) => {
    useEffect(() => {
        const script = document.createElement("script");
        script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${apiKey}&autoload=false&libraries=services`;
        script.async = true;

        script.onload = () => {
            if (window.kakao && window.kakao.maps) {
                window.kakao.maps.load(() => {
                    const kakao = window.kakao;
                    const mapContainer = document.getElementById("map");
                    const mapOption = {
                        center: new kakao.maps.LatLng(37.5665, 126.9783),
                        level: 3,
                    };

                    const map = new kakao.maps.Map(mapContainer, mapOption);
                    const ps = new kakao.maps.services.Places();

                    const searchBox = document.getElementById("search-box");
                    searchBox.addEventListener("keypress", function (event) {
                        if (event.key === "Enter") {
                            event.preventDefault();
                            ps.keywordSearch(searchBox.value, placesSearchCB);
                        }
                    });
                    

                    const markers = [];
                    function removeAllMarkers() {
                        for (let i = 0; i < markers.length; i++) {
                            markers[i].setMap(null);
                        }
                        markers.length = 0;
                    }

                    function placesSearchCB(data, status) {
                        if (status === kakao.maps.services.Status.OK) {
                            removeAllMarkers();
                            for (let i = 0; i < data.length; i++) {
                                const place = data[i];
                                const lat = place.y;
                                const lng = place.x;

                                const marker = new kakao.maps.Marker({
                                    map: map,
                                    position: new kakao.maps.LatLng(lat, lng),
                                });

                                kakao.maps.event.addListener(marker, "click", () => {
                                    const infowindow = new kakao.maps.InfoWindow({
                                        content: `
                                            <div style="padding:5px;">
                                                <strong>${place.place_name}</strong><br>
                                                주소: ${place.address_name || "정보 없음"}<br>
                                                전화번호: ${place.phone || "정보 없음"}<br>
                                            </div>
                                        `,
                                    });
                                    infowindow.open(map, marker);

                                    if (onLocationSelect) {
                                        onLocationSelect(lat, lng, place.place_name, place.address_name);
                                    }
                                });

                                map.setCenter(new kakao.maps.LatLng(lat, lng));
                                markers.push(marker);
                            }
                        } else {
                            console.error("검색 결과가 없습니다.");
                        }
                    }

                    kakao.maps.event.addListener(map, "click", function (mouseEvent) {
                        const lat = mouseEvent.latLng.getLat();
                        const lng = mouseEvent.latLng.getLng();
                        if (onLocationSelect) {
                            onLocationSelect(lat, lng);
                        }
                    });
                });
            } else {
                console.error("Kakao Maps SDK 로드에 실패했습니다.");
            }
        };

        script.onerror = () => {
            console.error("Kakao Maps SDK 스크립트를 불러오는 데 실패했습니다.");
        };

        document.head.appendChild(script);

        return () => {
            document.head.removeChild(script);
        };
    }, []);

    return (
        <MapContainer>
            <SearchBox id="search-box" placeholder="장소를 검색하세요" />
            <MapDiv id="map"></MapDiv>
        </MapContainer>
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
