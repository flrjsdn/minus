import axios from 'axios';

const apiUrl = process.env.REACT_APP_BACKEND_API_URL;

const MainpageStoreDetailApi = async ({ coords, receivedData, abortController }) => {
    try {
        const lat = coords.lat;
        const lng = coords.lng;

        // AbortController가 없으면 새로 생성
        const controller = abortController || new AbortController();

        // API 호출
        const response = await axios.get(`${apiUrl}/api/stores/list/`, {
            params: { lat, lng },
            signal: controller.signal, // AbortController의 signal 전달
        });

        const nearStorelist = response.data;

        if (response.status === 200) {
            alert('API 호출 성공!');
            receivedData(nearStorelist); // 데이터를 부모 컴포넌트로 전달
        } else {
            console.error('API 요청 실패:', nearStorelist.message || '알 수 없는 오류');
            alert('API 호출 실패!');
        }
    } catch (error) {
        if (axios.isCancel(error)) {
            console.log('요청이 취소되었습니다:', error.message);
        } else if (error.response) {
            console.error('API 호출 중 오류 발생:', error.response.data);
            alert(`API 호출 중 오류가 발생했습니다: ${error.response.data.message}`);
        } else if (error.request) {
            console.error('응답을 받지 못했습니다:', error.request);
            alert('API 호출 중 응답이 없습니다.');
        } else {
            console.error('요청 설정 중 오류 발생:', error.message);
            alert('API 호출 중 알 수 없는 오류가 발생했습니다.');
        }

        // 요청 취소
        if (abortController) {
            abortController.abort();
        }
    }
};

export default MainpageStoreDetailApi
