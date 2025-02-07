import React, { useState } from 'react';
import { BarcodeScanner } from '@thewirv/react-barcode-scanner';
import apiClient from '../../api/apiClient';

const CouponScannerComponent = ({ onConfirm }) => {
    const [isScanningAllowed, setIsScanningAllowed] = useState(true);

    const couponScanSuccess = async () => {
        if (!isScanningAllowed) return;
        setIsScanningAllowed(false);
        setTimeout(() => setIsScanningAllowed(true), 1000);

        try {
            const storeNo = 1;
            const couponId = 1;
            const userNo = 1;

            // apiClient를 사용하여 API 호출
            const response = await apiClient.get('api/coupon/use', {
                params: { storeNo, couponId, userNo },
            });

            const product = response.data;
            if (response.status === 200) {
                alert('테스트 API 호출 성공!');
                if (onConfirm) {
                    onConfirm(response.data); // 필요한 데이터를 전달할 수도 있음
                }
            } else {
                console.error('API 요청 실패:', product.message || '알 수 없는 오류');
                alert('테스트 API 호출 실패!');
            }
        } catch (error) {
            console.error('테스트 API 호출 중 오류 발생:', error);
            if (error.response && error.response.data) {
                alert(`테스트 API 호출 중 오류가 발생했습니다: ${error.response.data.message}`);
            } else {
                alert('테스트 API 호출 중 알 수 없는 오류가 발생했습니다.');
            }
        }
    };

    return (
        <div>
            <BarcodeScanner
                onSuccess={couponScanSuccess}
                onError={(error) => console.error('스캐너 오류:', error)}
            />
        </div>
    );
};

export default CouponScannerComponent;
