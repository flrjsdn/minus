import React, { useState, useEffect } from "react";

const useGeolocation = () => {
    const [address, setAddress] = useState('');
    const [coords, setCoords] = useState({lat: 37.5012767, lng: 127.0396002});
    const [error, setError] = useState(null);

    // Reverse Geocoding 함수
    const reverseGeocode = async (lat, lng) => {
        try {
            const response = await fetch(
                `https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${lng}&y=${lat}`,
                {
                    headers: {
                        Authorization: `KakaoAK ${process.env.REACT_APP_KAKAO_REST_API_KEY}`,
                    },
                }
            );
            const data = await response.json();
            return data.documents[0].address.address_name;
        } catch (err) {
            throw new Error('주소 변환 실패');
        }
    };

    // 위치 정보 조회
    const getLocation = async (lat = null, lng = null) => {
        if (!lat || !lng) {
            if (!navigator.geolocation) {
                setError('Geolocation을 지원하지 않는 브라우저입니다.');
                return;
            }

            navigator.geolocation.getCurrentPosition(
                async (position) => {
                    const { latitude, longitude } = position.coords;
                    try {
                        const addr = await reverseGeocode(latitude, longitude);
                        setAddress(addr);
                        setCoords({ lat: latitude, lng: longitude });
                    } catch (err) {
                        setError(err.message);
                    }
                },
                (error) => {
                    setError('위치 정보를 가져올 수 없습니다.');
                }
            );
        } else {
            try {
                const addr = await reverseGeocode(lat, lng);
                setAddress(addr);
                setCoords({ lat, lng });
            } catch (err) {
                setError(err.message);
            }
        }
    };

    useEffect(() => {
        getLocation();
    }, []);

    return { address, coords, error, refreshLocation: getLocation };
};

export default useGeolocation;
