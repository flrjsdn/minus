// import React, { useState } from 'react';
// import { BarcodeScanner } from '@thewirv/react-barcode-scanner';
//
//
// // 바코드 스캐너 컴포넌트
// const BarcodeScannerComponent = ({ onAddToCart }) => {
//     const handleScanSuccess = async (barcode) => {
//         try {
//             // 서버에서 제품 정보 가져오기
//             const response = await fetch('https://api.example.com/products', {
//                 method: 'POST',
//                 headers: { 'Content-Type': 'application/json' },
//                 body: JSON.stringify({ barcodeNumber: barcode }),
//             });
//             const product = await response.json();
//
//             if (response.ok) {
//                 // 부모 컴포넌트로 제품 정보 전달
//                 onAddToCart(product);
//             } else {
//                 console.error('API 요청 실패:', product.message || '알 수 없는 오류');
//                 alert('제품 정보를 가져오는 데 실패했습니다.');
//             }
//         } catch (error) {
//             console.error('제품 정보를 가져오는 중 오류 발생:', error);
//             alert('제품 정보를 가져오는 데 실패했습니다. 다시 시도해주세요.');
//         }
//     };
//
//     return (
//         <div>
//             <h2>바코드 스캔</h2>
//             <BarcodeScanner
//                 onSuccess={handleScanSuccess}
//                 onError={(error) => console.error('스캐너 오류:', error)}
//                 containerStyle={{ width: '300px', height: '300px', border: '1px solid black' }}
//             />
//         </div>
//     );
// };
//
// // 장바구니 컴포넌트
// const ShoppingCart = ({ cartItems, onIncrement, onDecrement }) => {
//     const totalPrice = cartItems.reduce(
//         (total, item) => total + item.price * item.quantity,
//         0
//     );
//
//     return (
//         <div>
//             {cartItems.length === 0 ? (
//                 <p>장바구니가 비어 있습니다.</p>
//             ) : (
//                 <ul>
//                     {cartItems.map((item) => (
//                         <li key={item.id}>
//                             {item.itemName} - {item.price.toLocaleString()}원 × {item.quantity} ={' '}
//                             {(item.price * item.quantity).toLocaleString()}원
//                             <button onClick={() => onIncrement(item.id)}>+</button>
//                             <button onClick={() => onDecrement(item.id)}>-</button>
//                         </li>
//                     ))}
//                 </ul>
//             )}
//             <h3>총 금액: {totalPrice.toLocaleString()}원</h3>
//         </div>
//     );
// };
//
// // 메인 컴포넌트
// const CartPage = () => {
//     const [cartItems, setCartItems] = useState([]);
//
//     // 장바구니에 제품 추가
//     const handleAddToCart = (product) => {
//         setCartItems((prevItems) => {
//             const existingItem = prevItems.find((item) => item.id === product.id);
//             if (existingItem) {
//                 return prevItems.map((item) =>
//                     item.id === product.id
//                         ? { ...item, quantity: item.quantity + 1 }
//                         : item
//                 );
//             } else {
//                 return [...prevItems, { ...product, quantity: 1 }];
//             }
//         });
//     };
//
//     // 수량 증가
//     const handleIncrement = (id) => {
//         setCartItems((prevItems) =>
//             prevItems.map((item) =>
//                 item.id === id ? { ...item, quantity: item.quantity + 1 } : item
//             )
//         );
//     };
//
//     // 수량 감소
//     const handleDecrement = (id) => {
//         setCartItems((prevItems) =>
//             prevItems.map((item) =>
//                 item.id === id && item.quantity > 1
//                     ? { ...item, quantity: item.quantity - 1 }
//                     : item
//             )
//         );
//     };
//
//     return (
//         <div>
//             <div className="ShoppingCart">
//                 <ShoppingCart
//                     cartItems={cartItems}
//                     onIncrement={handleIncrement}
//                     onDecrement={handleDecrement}
//                 />
//             </div>
//             <div className="BarcodeScanner">
//                 <BarcodeScannerComponent onAddToCart={handleAddToCart} />
//             </div>
//         </div>
//     );
// };
//
// export default CartPage;

import React, { useState } from 'react';
import { BarcodeScanner } from '@thewirv/react-barcode-scanner';
import './style.css';
import PaymentPopup from '../../components/PaymentPopup'; // 결제 팝업 컴포넌트

// 더미 데이터
const dummyProducts = [
    { id: '1', itemName: '무선 이어폰', price: 99000, quantity: 1 },
    { id: '2', itemName: '스마트 워치', price: 199000, quantity: 1 },
    { id: '3', itemName: '블루투스 스피커', price: 59000, quantity: 1 },
    { id: '4', itemName: '게이밍 마우스', price: 45000, quantity: 1 },
    { id: '5', itemName: '기계식 키보드', price: 120000, quantity: 1 },
];

// 장바구니 컴포넌트
const ShoppingCart = ({ cartItems, onIncrement, onDecrement, onRemove }) => {
    const totalPrice = cartItems.reduce(
        (total, item) => total + item.price * item.quantity,
        0
    );

    return (
        <div className="ShoppingCart">
            {cartItems.length === 0 ? (
                <p>장바구니가 비어 있습니다.</p>
            ) : (
                <ul>
                    {cartItems.map((item) => (
                        <li key={item.id} className="CartItem">
                            <div className="Item-Name">{item.itemName}</div>
                            <div className="Item-Price">{(item.price * item.quantity).toLocaleString()} 원</div>
                            <div className="QuantityControls">
                                <button onClick={() => onDecrement(item.id)}>-</button>
                                <span>{item.quantity}</span>
                                <button onClick={() => onIncrement(item.id)}>+</button>
                            </div>
                            <button className="DeleteButton" onClick={() => onRemove(item.id)}>삭제</button>
                        </li>
                    ))}
                </ul>
            )}
            <div className="Total-Price">총 금액: {totalPrice.toLocaleString()} 원</div>
        </div>
    );
};

// 메인 컴포넌트
const CartPage = () => {
    const [cartItems, setCartItems] = useState(dummyProducts);
    const [isPopupOpen, setIsPopupOpen] = useState(false);

    // 장바구니에 제품 추가
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

    // 수량 증가
    const handleIncrement = (id) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.id === id ? { ...item, quantity: item.quantity + 1 } : item
            )
        );
    };

    // 수량 감소
    const handleDecrement = (id) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.id === id && item.quantity > 1
                    ? { ...item, quantity: item.quantity - 1 }
                    : item
            )
        );
    };

    // 제품 삭제
    const handleRemove = (id) => {
        setCartItems((prevItems) => prevItems.filter((item) => item.id !== id));
    };

    // 결제 팝업 열기/닫기
    const handleOpenPopup = () => setIsPopupOpen(true);
    const handleClosePopup = () => setIsPopupOpen(false);

    // 결제 완료 처리
    const handleConfirmPayment = (method) => {
        alert(`${method}로 결제가 완료되었습니다.`);
        setIsPopupOpen(false);
        setCartItems([]); // 결제 후 장바구니 초기화
    };

    return (
        <div>
            {/* 장바구니 UI */}
            <ShoppingCart
                cartItems={cartItems}
                onIncrement={handleIncrement}
                onDecrement={handleDecrement}
                onRemove={handleRemove}
            />

            {/* 바코드 스캐너 */}
            <div className="BarcodeScanner">
                <BarcodeScannerComponent onAddToCart={handleAddToCart} />
            </div>

            {/* 결제하기 버튼 */}
            <button className="payment" onClick={handleOpenPopup}>
                결제하기
            </button>

            {/* 결제 팝업 */}
            {isPopupOpen && (
                <PaymentPopup
                    onClose={handleClosePopup}
                    onConfirm={handleConfirmPayment}
                />
            )}
        </div>
    );
};

// 바코드 스캐너 컴포넌트
const BarcodeScannerComponent = ({ onAddToCart }) => {
    const handleScanSuccess = () => {
        const dummyProduct = {
            id: '6',
            itemName: '휴대용 충전기',
            price: 30000,
            quantity: 1,
        };
        onAddToCart(dummyProduct);
    };

    return (
        <div>
            <h2>바코드 스캔</h2>
            <button onClick={handleScanSuccess}>임의의 제품 추가</button>
        </div>
    );
};

export default CartPage;

