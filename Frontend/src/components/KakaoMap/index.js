import React, { useEffect } from "react";
import styled from "styled-components";

function KakaoMap() {
  useEffect(() => {
    const existingScript = document.getElementById("kakao-map-script");
    if (!existingScript) {
      const script = document.createElement("script");
      script.id = "kakao-map-script";
      script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.REACT_APP_KAKAO_JS_API_KEY}&autoload=false`;
      script.async = true;
      script.onload = () => {
        console.log("Kakao Maps script loaded");
        if (window.kakao && window.kakao.maps) {
          window.kakao.maps.load(() => {
            const container = document.getElementById("map");
            const options = {
              center: new window.kakao.maps.LatLng(37.5665, 126.9780),
              level: 3,
            };
            new window.kakao.maps.Map(container, options);
          });
        } else {
          console.error("window.kakao 또는 window.kakao.maps가 정의되지 않았습니다.");
        }
      };
      script.onerror = () => {
        console.error("Failed to load Kakao Maps script");
      };
      document.head.appendChild(script);
    } else {
      window.kakao.maps.load(() => {
        const container = document.getElementById("map");
        const options = {
          center: new window.kakao.maps.LatLng(37.5665, 126.9780),
          level: 3,
        };
        new window.kakao.maps.Map(container, options);
      });
    }
  }, []);

  return <MapContainer id="map" />;
}

export default KakaoMap;

const MapContainer = styled.div`
  width: 500px;
  height: 500px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  background-color: #f0f0f0;
`;
