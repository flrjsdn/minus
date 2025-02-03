import React, { useState } from 'react';
import { BarcodeScanner } from '@thewirv/react-barcode-scanner';

// 바코드 스캐너 컴포넌트
const BarcodeScannerComponent = ({ onAddToCart }) => {
    const handleScanSuccess = async (barcode) => {
        try {
            // 서버에서 제품 정보 가져오기
            const response = await fetch('https://api.example.com/products', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ barcodeNumber: barcode }),
            });
            const product = await response.json();

            if (response.ok) {
                // 부모 컴포넌트로 제품 정보 전달
                onAddToCart(product);
            } else {
                console.error('API 요청 실패:', product.message || '알 수 없는 오류');
                alert('제품 정보를 가져오는 데 실패했습니다.');
            }
        } catch (error) {
            console.error('제품 정보를 가져오는 중 오류 발생:', error);
            alert('제품 정보를 가져오는 데 실패했습니다. 다시 시도해주세요.');
        }
    };

    return (
        <div>
            <h2>바코드 스캔</h2>
            <BarcodeScanner
                onSuccess={handleScanSuccess}
                onError={(error) => console.error('스캐너 오류:', error)}
                containerStyle={{ width: '300px', height: '300px', border: '1px solid black' }}
            />
        </div>
    );
};

// 장바구니 컴포넌트
const ShoppingCart = ({ cartItems }) => {
    return (
        <div>
            <h2>장바구니</h2>
            {cartItems.length === 0 ? (
                <p>장바구니가 비어 있습니다.</p>
            ) : (
                <ul>
                    {cartItems.map((item, index) => (
                        <li key={index}>
                            {item.itemName} - {item.price.toLocaleString()}원
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

// 메인 컴포넌트
const App = () => {
    const [cartItems, setCartItems] = useState([]);

    // 장바구니에 제품 추가
    const handleAddToCart = (product) => {
        setCartItems((prevItems) => [...prevItems, product]);
    };

    return (
        <div>
            <BarcodeScannerComponent onAddToCart={handleAddToCart} />
            <ShoppingCart cartItems={cartItems} />
        </div>
    );
};

export default App;
