import React from 'react';

const CartPage = ({ cartItems, onIncrement, onDecrement, onRemove }) => {
    const totalPrice = cartItems.reduce(
        (total, item) => total + item.price * item.quantity,
        0
    );

    return (
        <div>
            {cartItems.length === 0 ? (
                <p>장바구니가 비어 있습니다.</p>
            ) : (
                <ul>
                    {cartItems.map((item) => (
                        <li key={item.id}>
                            {item.itemName} - {item.price.toLocaleString()}원 × {item.quantity} ={' '}
                            {(item.price * item.quantity).toLocaleString()}원
                            <button onClick={() => onIncrement(item.id)}>+</button>
                            <button onClick={() => onDecrement(item.id)}>-</button>
                            <button onClick={() => onRemove(item.id)}>삭제</button>
                        </li>
                    ))}
                </ul>
            )}
            <h3>총 금액: {totalPrice.toLocaleString()}원</h3>
        </div>
    );
};

export default CartPage;