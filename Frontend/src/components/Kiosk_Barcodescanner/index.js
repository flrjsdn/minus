import React from 'react';
import { BarcodeScanner } from '@thewirv/react-barcode-scanner';
import axios from "axios";

const BarcodeScannerComponent = ({ onAddToCart }) => {
    const handleScanSuccess = async () => {
        try {
            const storeNo = 1;
            const barcode = 8801118250590;

            const response = await axios.get(
                `http://i12a506.p.ssafy.io:8000/api/kiosk/scan`,
                {
                    params: { storeNo, barcode },
                    headers: { 'Content-Type': 'application/json' },
                }
            );

            const product = response.data;

            if (response.status === 200) {
                alert('테스트 API 호출 성공!');
                onAddToCart({
                    id: product.itemId,
                    itemName: product.itemName,
                    price: product.price,
                });
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
                onSuccess={handleScanSuccess}
                onError={(error) => console.error('스캐너 오류:', error)}
            />
        </div>
    );
};

export default BarcodeScannerComponent;

