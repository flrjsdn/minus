import React, { useEffect } from "react";

const apiKey = process.env.REACT_APP_KAKAO_JS_API_KEY;

const KakaoMapBackground = () => {
    useEffect(() => {
        // Kakao Maps SDK 스크립트를 동적으로 로드
        const script = document.createElement("script");
        script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${apiKey}&autoload=false`;
        script.async = true;

        script.onload = () => {
            // Kakao 객체가 로드되었는지 확인
            if (window.kakao && window.kakao.maps) {
                // Kakao Maps API를 수동으로 초기화
                window.kakao.maps.load(() => {
                    const kakao = window.kakao;
                    const mapContainer = document.getElementById("map"); // 지도를 표시할 div
                    const mapOption = {
                        center: new kakao.maps.LatLng(33.450701, 126.570667), // 중심 좌표
                        level: 3, // 확대 레벨
                    };

                    // 지도 생성
                    new kakao.maps.Map(mapContainer, mapOption);
                });
            } else {
                console.error("Kakao Maps SDK 로드에 실패했습니다.");
            }
        };

        script.onerror = () => {
            console.error("Kakao Maps SDK 스크립트를 불러오는 데 실패했습니다.");
        };

        document.head.appendChild(script);

        // 컴포넌트 언마운트 시 스크립트 제거
        return () => {
            document.head.removeChild(script);
        };
    }, []);

    return (
        <div
            id="map"
            style={{ width: "360px", height: "800px" }}
        ></div>
    );
};

export default KakaoMapBackground;
