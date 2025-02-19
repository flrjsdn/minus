import apiClient from "./apiClient";
import Swal from "sweetalert2";

const CouponGetApi = async (nStoreNo, couponId) => {
    try {
        const response = await apiClient.post(`/api/coupon/receive`, {
            storeNo: nStoreNo,
            couponId: couponId,
        });

        // HTTP 상태 코드 분기 처리
        if (response.status >= 200 && response.status < 300) {
            Swal.fire({
                icon: "success",
                title: "요청 완료!",
                text: "🎉 쿠폰 발급 성공!",
            });
            return response.data;
        }

        // 서버 커스텀 에러 처리
        const serverError = response.data || {};
        const errorMessage = serverError.message || "쿠폰 처리 중 오류 발생";
        const errorCode = serverError.errorCode || "UNKNOWN_ERROR";

        // 중복 수령 케이스 (40930)
        if (errorCode === 40930) {
            Swal.fire({
                icon: "error",
                title: "오류 발생!",
                text: `${errorMessage}`,
            });
            return { isError: true, errorCode };
        }

        // 기타 서버 에러
        Swal.fire({
            icon: "error",
            title: "오류 발생!",
            text: `❗ ${errorMessage}`,
        });
        throw new Error(`[${errorCode}] ${errorMessage}`);

    } catch (error) {
        // 네트워크 레벨 에러
        if (!error.response) {
            const networkErrorMsg = error.message.includes('Network Error')
                ? '서버 연결 실패'
                : error.message;
            Swal.fire({
                icon: "error",
                title: "오류 발생!",
                text: `🚨 시스템 오류: ${networkErrorMsg}`,
            });
            throw new Error(`NETWORK_ERROR: ${networkErrorMsg}`);
        }

        // HTTP 에러 응답 처리
        const status = error.response.status;
        const serverMessage = error.response.data?.message
            || `서버 오류 (${status})`;
        Swal.fire({
            icon: "error",
            title: "오류 발생!",
            text: ` ${serverMessage}`,
        });
        return {
            isError: true,
            errorCode: error.response.data?.errorCode || status
        };
    }
};

export default CouponGetApi;