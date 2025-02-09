import { useState, useEffect } from "react";
import axios from "axios";

function useAuth() {
    const [logindata, setLogindata] = useState(null);

    useEffect(() => {
        // 사용자가 로그인한 상태인지 확인하는 API 요청
        const checkLoginStatus = async () => {
            try {
                const response = await axios.get('http://i12a506.p.ssafy.io:8000/api/users/consumer', {
                    withCredentials: true,  // 쿠키 포함
                });

                // 일반 사용자일 경우
                if (response.status === 200) {
                    setLogindata(response.data);  // 로그인된 사용자 정보 저장
                } else {
                    // 점주일 경우 다른 URI로 요청
                    const storeOwnerResponse = await axios.get('http://i12a506.p.ssafy.io:8000/api/users/store-owner', {
                        withCredentials: true,  // 쿠키 포함
                    });

                    if (storeOwnerResponse.status === 200) {
                        setLogindata(storeOwnerResponse.data);  // 점주 정보 저장
                    } else {
                        setLogindata(null);  // 로그인되지 않은 상태
                    }
                }
            } catch (error) {
                console.error("로그인 상태 확인 오류:", error);
                setLogindata(null);
            }
        };

        checkLoginStatus();
    }, []);  // 처음 로드 시 한번만 실행

    return { logindata };
}

export default useAuth;
