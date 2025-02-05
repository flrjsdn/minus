import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import BarcodeScannerComponent from "../../components/Kiosk_Barcodescanner";
import KioskHeaderContainer from "../../components/Kiosk_HeaderContainer";
import Cartpage from "../../components/Kiosk_CartPage";
import KioskList from "../../components/Kiosk_List";
import Kiosk_FleaProductList from "../../components/Kiosk_FleaProductList";
import PaymentPopup from '../../components/Kiosk_PaymentPopup'; // 결제 팝업 컴포넌트
import './main.css'
import kiosk from "./index";

const KioskMainScreen = () => {
    const [cartItems, setCartItems] = useState([]);
    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const navigate = useNavigate();

    const kioskhomeclick = () => {
        navigate('/kiosk');
    };

    const handleAddToCart = (product) => {
        setCartItems((prevItems) => {
            // itemName 기준으로 기존 장바구니에서 같은 이름의 제품 찾기
            const existingItem = prevItems.find((item) => item.itemName === product.itemName);

            if (existingItem) {
                // 이미 존재하면 수량(quantity) 증가
                return prevItems.map((item) =>
                    item.itemName === product.itemName
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            } else {
                // 존재하지 않으면 새로 추가
                return [...prevItems, { ...product, quantity: 1 }];
            }
        });
    };


    const handleIncrement = (itemName) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.itemName === itemName ? { ...item, quantity: item.quantity + 1 } : item
            )
        );
    };

    const handleDecrement = (itemName) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.itemName === itemName && item.quantity > 1
                    ? { ...item, quantity: item.quantity - 1 }
                    : item
            )
        );
    };

    const handleRemove = (itemName) => {
        setCartItems((prevItems) => prevItems.filter((item) => item.itemName !== itemName));
    };


    // 총 금액 계산 함수
    const calculateTotalPrice = () => {
        return cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
    };

    const handleOpenPopup = () => setIsPopupOpen(true);
    const handleClosePopup = () => setIsPopupOpen(false);

    const handleConfirmPayment = (method) => {
        alert(`${method}로 결제가 완료되었습니다.`);
        setIsPopupOpen(false);
        setCartItems([]); // 결제 후 장바구니 초기화
    };

    return (
        <div className="kioskmainscreen">
            <div className="mainscreencontainer"><KioskHeaderContainer /></div>
            <div className="mainscreennotice">장 바 구 니</div>
            <div className="mainscreenkiosklist"><KioskList /></div>
            <div className="mainscreencartpage">
                <Cartpage
                    cartItems={cartItems}
                    onIncrement={handleIncrement}
                    onDecrement={handleDecrement}
                    onRemove={handleRemove}
                />
            </div>
            <div className="kioskpleaproductlist"><Kiosk_FleaProductList onAddToCart={handleAddToCart}/></div>
            <div className="barcodebuttoncontainer">
                <div className="mainscreenbarcodescanner"><BarcodeScannerComponent onAddToCart={handleAddToCart} /></div>
                <div className="buttonzone">
                    <div className="cartpagetotal">총 금액: {calculateTotalPrice().toLocaleString()}원</div>
                    <button className="mainscreenpayment" onClick={handleOpenPopup}>결제하기</button>
                    <button className="mainscreentohome" onClick={kioskhomeclick}>홈으로</button>
                </div>
            </div>

            {isPopupOpen && (
                <PaymentPopup
                    onClose={handleClosePopup}
                    onConfirm={handleConfirmPayment}
                />
            )}
        </div>
    );
};

export default KioskMainScreen;
