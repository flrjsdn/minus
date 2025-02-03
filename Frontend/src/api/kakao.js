import axios from 'axios';

const KAKAO_API_URL = 'https://kauth.kakao.com/oauth/token'; // 카카오 토큰 요청 URL

export const getKakaoAccessToken = async (code) => {
    try {
        const response = await axios.post(KAKAO_API_URL, null, {
            params: {
                grant_type: 'authorization_code',
                client_id: process.env.REACT_APP_KAKAO_REST_API_KEY, // .env에서 키를 가져옴
                redirect_uri: process.env.REACT_APP_KAKAO_REDIRECT_URI, // 리디렉션 URI
                code: code,
            }
        });
        return response.data; // 액세스 토큰 데이터 반환
    } catch (error) {
        console.error('카카오 로그인 실패', error);
        throw error;
    }
};
