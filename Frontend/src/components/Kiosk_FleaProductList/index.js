import React from "react";
import apiClient from "../../api/apiClient";
import './style.css';

const FleaProductList = ({ onAddToCart }) => {
    const buttonclicksuccess = async (section) => {
        try {
            const storeNo = 1;
            const sectionId= 1;

            const response = await apiClient.get('kiosk/flea-item', {
                params: { storeNo, sectionId },
            });

            const product = response.data;

            if (response.status === 200) {
                alert('API 호출 성공!');
                onAddToCart({
                    id: product.itemId,
                    itemName: product.itemName,
                    price: product.price,
                });
            } else {
                console.error('API 요청 실패:', product.message || '알 수 없는 오류');
                alert('API 호출 실패!');
            }
        } catch (error) {
            console.error('API 호출 중 오류 발생:', error);

            if (error.response && error.response.data) {
                alert(`API 호출 중 오류가 발생했습니다: ${error.response.data.message}`);
            } else {
                alert('API 호출 중 알 수 없는 오류가 발생했습니다.');
            }
        }
    };

    return (
        <div className="kioskfleamarketsection">
            {[1, 2, 3, 4].map((section) => (
                <button
                    className="kioskfleamarketbutton"
                    key={section}
                    onClick={() => buttonclicksuccess(section)}
                >
                    플리제품{section}<br/> 스캔
                </button>
            ))}
        </div>
    );
};

export default FleaProductList;
