import React, { useState } from 'react';
import BarcodeScannerComponent from "../../components/Kiosk_Barcodescanner";
import KioskHeaderContainer from "../../components/Kiosk_HeaderContainer";
import Cartpage from "../../components/Kiosk_CartPage";
import KioskList from "../../components/Kiosk_List";
import PaymentPopup from '../../components/PaymentPopup'; // 결제 팝업 컴포넌트
import './main.css'

const KioskMainScreen = () => {
    const [cartItems, setCartItems] = useState([]);
    const [isPopupOpen, setIsPopupOpen] = useState(false);

    const handleAddToCart = (product) => {
        setCartItems((prevItems) => {
            const existingItem = prevItems.find((item) => item.id === product.id);
            if (existingItem) {
                return prevItems.map((item) =>
                    item.id === product.id
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            } else {
                return [...prevItems, { ...product, quantity: 1 }];
            }
        });
    };

    const handleIncrement = (id) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.id === id ? { ...item, quantity: item.quantity + 1 } : item
            )
        );
    };

    const handleDecrement = (id) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.id === id && item.quantity > 1
                    ? { ...item, quantity: item.quantity - 1 }
                    : item
            )
        );
    };

    const handleRemove = (id) => {
        setCartItems((prevItems) => prevItems.filter((item) => item.id !== id));
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
            <div className="mainscreenbarcodescanner"><BarcodeScannerComponent onAddToCart={handleAddToCart} /></div>

            <div className="buttonzone">
                <button className="mainscreenpayment" onClick={handleOpenPopup}>
                    결제하기
                </button>
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
