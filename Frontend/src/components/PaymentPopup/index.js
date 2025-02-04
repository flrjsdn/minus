import React from 'react';
import "./style.css";

const PaymentPopup = ({ onClose, onConfirm }) => {
    return (
        <div className="payment-popup">
            <div className="popup-content">
                <h2>결제 수단 선택</h2>
                <div className="payment-options">
                    <button onClick={() => onConfirm('카카오페이')}>카드 결제</button>
                    <button onClick={() => onConfirm('네이버페이')}>현금 결제</button>
                </div>
                <button className="close-button" onClick={onClose}>닫기</button>
            </div>
        </div>
    );
};

export default PaymentPopup;
