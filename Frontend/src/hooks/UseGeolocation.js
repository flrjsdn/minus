import { useState, useEffect } from "react";

const useGeolocation = () => {
    const [coords, setCoords] = useState({lat: 37.5012767, lng: 127.0396002} );
    const [error, setError] = useState(null);

    const getLocation = () => {
        if (!navigator.geolocation) {
            setError("Geolocation을 지원하지 않는 브라우저입니다.");
            return;
        }

        navigator.geolocation.getCurrentPosition(
            (position) => {
                const { latitude, longitude } = position.coords;
                setCoords({ lat: latitude, lng: longitude });
            },
            () => {
                setError("위치 정보를 가져올 수 없습니다.");
            }
        );
    };

    useEffect(() => {
        getLocation();
    }, []);

    return { coords, error };
};

export default useGeolocation;
