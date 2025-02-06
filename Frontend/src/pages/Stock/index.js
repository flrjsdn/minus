import React, { useState,useEffect } from 'react';
import axios from 'axios';
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";
import styled from 'styled-components';


function Stock() {
    const storeNo = 1;  // 임의로 storeNo를 1로 설정

    const [products, setProducts] = useState([]);

    useEffect(() => {
        console.log("storeNo:", storeNo);  // 디버깅용 콘솔 출력
        if (!storeNo) return;

        const fetchProducts = async () => {
            try {
                const response = await axios.get(`http://i12a506.p.ssafy.io:8000/api/item/regist_list`, {
                params: { storeNo }
                });
                setProducts(response.data);
            } catch (error) {
                console.error("입고요청 목록 조회 실패:", error);
                
            }
        };

        fetchProducts();
    }, [storeNo]);

    return (
        <div>
                     <HeaderContainer />
                    <MyPageHeader/>
            <h2>상품 목록</h2>
            {products.length > 0 ? (
                products.map((product, index) => (
                    <div key={index}>
                        <p>상품명: {product.itemName}</p>
                        <p>요청 수량: {product.requestCount}</p>
                    </div>
                ))
            ) : (
                <p>상품이 없습니다.</p>
            )}
                  <BottomNav />

        </div>
    );
}

export default Stock;



// function Stock({ storeNo }) {

//     const [products, setProducts] = useState([]);
//     useEffect(() => {
//       // storeNo가 없으면 API 호출 안 함
//       if (!storeNo) return;
  
//       // 상품 목록 조회 함수
//       const fetchProducts = async () => {
//         try {
//           // storeNo를 쿼리 파라미터로 포함하여 GET 요청
//           const response = await axios.get(`/api/item/regist_list`, {
//             params: { storeNo }
//           });
  
//           // 조회된 상품 목록 저장
//           setProducts(response.data);
//         } catch (error) {
//           console.error("상품 목록 조회 실패:", error);
//         }
//       };
  
//       fetchProducts();
//     }, [storeNo]); // storeNo가 변경될 때마다 요청 실행

//     return (
//         <div>
//         <HeaderContainer />
//         <MyPageHeader/>
//           <h2>상품 목록</h2>
//           {products.length > 0 ? (
//             products.map((product, index) => (
//               <div key={index}>
//                 <p>상품명: {product.itemName}</p>
//                 <p>요청 수량: {product.requestCount}</p>
//               </div>
//             ))
//           ) : (
//             <p>상품이 없습니다.</p>
//           )}
//         <BottomNav />
//         </div>
//       );
//     }
    
//     export default Stock;