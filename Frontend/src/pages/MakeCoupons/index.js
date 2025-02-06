import React, { useState } from "react";
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";
import { makecouponsData } from "../../dummydata/makecoupons";
import styled from "styled-components";

function MakeCoupons() {
    const [coupons, setCoupons] = useState(makecouponsData);
    const [count, setCount] = useState(0); // 초기 수량 0 설정정
    const [expirationDate, setExpirationDate] = useState("");

    // 쿠폰 등록 함수
    const handleAddCoupon = () => {
        if (!count || !expirationDate) {
            alert("모든 항목을 입력하세요.");
            return;
        }

        const newCoupon = {
            storeNo: coupons.length + 1, 
            couponId: coupons.length + 1, 
            count: parseInt(count, 10),
            expirationDate,
        };

        setCoupons([...coupons, newCoupon]);

        // 입력 필드 초기화
        setCount(0);
        setExpirationDate("");
    };
    
    // 쿠폰 개수 증감
    const increaseCount = () => {
        setCount((prevCount) => prevCount+1);
    };

    const decreaseCount = () => {
        if (count>0) {
            setCount((prevCount) => prevCount-1);
        }
    };

    return (
        <Container>
            <HeaderContainer />
            <MyPageHeader />
            <h2>쿠폰 등록 페이지</h2>
            <p>여기서 점주가 쿠폰을 등록할 수 있습니다.</p>

            {/* 쿠폰 입력 폼 */}
            <Form>

                <CountWrapper>
                    <Input 
                        type="number" 
                        placeholder="쿠폰 수량" 
                        value={count}
                        onChange={(e) => setCount(parseInt(e.target.value,10)||0)}
                        />
                    <Button onClick={decreaseCount}>-</Button>
                    <Button onClick={increaseCount}>+</Button>
                </CountWrapper>

                <Input 
                    type="date" 
                    placeholder="만료일" 
                    value={expirationDate}
                    onChange={(e) => setExpirationDate(e.target.value)}
                    max="9999-12-31"  // 최대값을 9999-12-31로 설정
                    min="1900-01-01"  // 최소값을 1900-01-01로 설정
                />
                <Button onClick={handleAddCoupon}>쿠폰 등록</Button>
            </Form>

            {/* 쿠폰 목록 */}
            <CouponTable>
                <thead>
                    <tr>
                        <th>쿠폰 ID</th>
                        <th>남은 수량</th>
                        <th>만료일</th>
                    </tr>
                </thead>
                <tbody>
                    {coupons.map((coupon) => (
                        <tr key={coupon.couponId}>
                            <td>{coupon.couponId}</td>
                            <td>{coupon.count}</td>
                            <td>{new Date(coupon.expirationDate).toLocaleDateString()}</td>
                        </tr>
                    ))}
                </tbody>
            </CouponTable>

            <BottomNav />
        </Container>
    );
}

export default MakeCoupons;

const Container = styled.div`
    background: #f8f9fa;
    text-align: center;
`;

const Form = styled.div`
    margin: 20px 0;
    display: flex;
    flex-direction: column;
    align-items: center;
`;

const Input = styled.input`
    width: 80%;
    padding: 15px;
    margin: 10px;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 8px;
`;

const CountWrapper = styled.div`
    display:flex;
    align-items: center;
    gap:5px;
`;

const Button = styled.button`
    padding: 10px 20px;
    font-size: 1rem;
    background-color: #3f72af;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;

    &:hover {
        background-color: #2c5aa0;
    }
`;

const CouponTable = styled.table`
    width: 95%;
    border-collapse: collapse;
    margin: 10px auto;

    th, td {
        border: 1px solid #ddd;
        padding: 10px;
    }

    th {
        background-color: #3f72af;
        color: white;
    }
`;
