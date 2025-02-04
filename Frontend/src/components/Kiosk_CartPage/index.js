import React from 'react';
import './style.css'


const CartPage = ({ cartItems, onIncrement, onDecrement, onRemove }) => {
    const totalPrice = cartItems.reduce(
        (total, item) => total + item.price * item.quantity,
        0
    );

    return (
        <div className="cartpagedom">
            <div className="cartpagelist">
                {cartItems.length === 0 ? (
                    <div className="cartpageemptynotice">장바구니가 비어 있습니다.</div>
                    ) : (
                    <ul>
                        {cartItems.map((item) => (
                            <li key={item.id} className="cartpageli">
                                {item.itemName}
                                <button onClick={() => onRemove(item.id)} className="cartpagedelete">삭제</button>
                                <div className="eachitemtotal">{(item.price * item.quantity).toLocaleString()}원</div>
                                <button onClick={() => onDecrement(item.id)} className="cartpageplmi1">-</button>
                                {item.quantity}
                                <button onClick={() => onIncrement(item.id)} className="cartpageplmi2">+</button>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
            <div className="cartpagetotal">총 금액: {totalPrice.toLocaleString()}원</div>
        </div>
    );
};

export default CartPage;