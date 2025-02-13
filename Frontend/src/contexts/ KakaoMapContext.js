import React, { createContext, useState, useContext, useEffect } from "react";

const KakaoMapContext = createContext();

export const KakaoMapProvider = ({ children }) => {
    const [map, setMap] = useState(null);
    const [isSDKLoaded, setIsSDKLoaded] = useState(false);

    // SDK 로딩 로직을 함수로 분리
    const loadKakaoSDK = () => {
        // 이미 스크립트가 존재하면 재사용, 없으면 추가
        const existingScript = document.querySelector(
            `script[src^="https://dapi.kakao.com/v2/maps/sdk.js"]`
        );
        if (existingScript) {
            // 이미 스크립트가 로드된 경우, SDK의 load 메서드를 재호출
            if (window.kakao && window.kakao.maps) {
                window.kakao.maps.load(() => {
                    setIsSDKLoaded(true);
                });
            }
        } else {
            const script = document.createElement("script");
            script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.REACT_APP_KAKAO_JS_API_KEY}&libraries=services&autoload=false`;
            script.async = true;

            script.onload = () => {
                window.kakao.maps.load(() => {
                    setIsSDKLoaded(true);
                });
            };

            script.onerror = () => console.error("Kakao Maps SDK 로드 실패");

            document.head.appendChild(script);
        }
    };

    useEffect(() => {
        // 처음 로드 시 SDK 로딩
        loadKakaoSDK();

        // bfcache(뒤로가기 등)에서 복원될 때 SDK 재로드 처리
        const handlePageShow = (event) => {
            // event.persisted 조건 없이 항상 SDK 재로딩 시도
            loadKakaoSDK();
        };

        window.addEventListener("pageshow", handlePageShow);
        return () => {
            window.removeEventListener("pageshow", handlePageShow);
        };
    }, []);

    return (
        <KakaoMapContext.Provider value={{ map, setMap, isSDKLoaded }}>
            {children}
        </KakaoMapContext.Provider>
    );
};

export const useKakaoMap = () => useContext(KakaoMapContext);
