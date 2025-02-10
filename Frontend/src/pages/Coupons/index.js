import React, { useEffect, useState } from "react";
import axios from "axios";

function CouponList() {
    const [coupons, setCoupons] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCoupons = async () => {
            try {
                const response = await axios.get("http://i12a506.p.ssafy.io:8000/api/coupon/receive/list", {
                    withCredentials:true, // 쿠키를 함께 전송
                });

                // 응답 데이터 콘솔 출력
                console.log("응답 데이터:", response.data);
                
                setCoupons(response.data);
            } catch (err) {
                console.error("쿠폰 데이터를 불러오는 중 오류 발생:", err);
                setError("쿠폰 데이터를 불러올 수 없습니다.");
            } finally {
                setLoading(false);
            }
        };
    
        fetchCoupons();
    }, []);

    if (loading) return <p>쿠폰을 불러오는 중...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div>
            <h2>받은 쿠폰 목록</h2>
            <ul>
                {coupons.map((coupon) => (
                    <li key={coupon.couponId} style={{ border: "1px solid #ccc", padding: "10px", marginBottom: "10px" }}>
                        <h3>{coupon.couponName}</h3>
                        <p>{coupon.content}</p>
                        <p><strong>가게:</strong> {coupon.storeName}</p>
                        <p><strong>할인율:</strong> {coupon.discountRate}%</p>
                        <p><strong>유효기간:</strong> {new Date(coupon.expirationDate).toLocaleDateString()}</p>
                        {coupon.usedAt ? (
                            <p style={{ color: "red" }}><strong>사용됨:</strong> {new Date(coupon.usedAt).toLocaleDateString()}</p>
                        ) : (
                            <p style={{ color: "green" }}>사용 가능</p>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default CouponList;

// // import { couponsData } from "../../dummydata/coupons"; // 더미 데이터
// import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
// import BottomNav from "../../components/BottomNav/BottomNav";
// import MyPageHeader from "../../components/MyPageHeader";
// import React, { useState, useEffect } from "react";
// import axios from "axios"; // axios 사용
// import styled from "styled-components";

// function Coupons() {
//     const [coupons, setCoupons] = useState([]); // API에서 가져온 쿠폰 데이터
//     const [loading, setLoading] = useState(true); // 로딩 상태
//     const [error, setError] = useState(null); // 에러 상태
//     const [selectedCoupon, setSelectedCoupon] = useState(null); // 선택된 쿠폰
//     const [barcode, setBarcode] = useState(null); // 바코드

//     useEffect(() => {
//         const fetchCoupons = async () => {
//             try {
//                 const response = await axios.get("http://i12a506.p.ssafy.io:8000/api/coupon/receive/list"); // API 호출
//                 setCoupons(response.data); // 쿠폰 데이터 업데이트
//             } catch (err) {
//                 setError("쿠폰 데이터를 가져오는데 실패했습니다.");
//             } finally {
//                 setLoading(false);
//             }
//         };

//         fetchCoupons(); // 데이터 로드
//     }, []);

//     const handleCouponClick = async (coupon) => {
//         try {
//             const requestDto = {
//                 couponId: coupon.couponId, // API에 맞게 couponId로 변경
//                 storeNo: coupon.storeNo, 
//                 userNo: coupon.userNo, // API에 맞게 userNo도 동적으로
//             };

//             const response = await axios.post("http://i12a506.p.ssafy.io:8000/api/coupon/barcode", requestDto); // API 요청
//             setBarcode(response.data.barcode);
//             setSelectedCoupon(coupon); 
//         } catch (err) {
//             setError("쿠폰 사용 중 오류가 발생했습니다.");
//         }
//     };

//     if (loading) {
//         return <p>로딩 중...</p>;
//     }

//     if (error) {
//         return <p>{error}</p>;
//     }

//     return (
//         <div>
//             <HeaderContainer />
//             <MyPageHeader />

//             <CouponsContainer>
//                 {coupons.length === 0 ? (
//                     <p>현재 보유한 쿠폰이 없습니다.</p>
//                 ) : (
//                     <CouponList>
//                         {coupons.map((coupon) => (
//                             <CouponCard key={coupon.couponId} onClick={() => handleCouponClick(coupon)}>
//                                 <CouponContent>
//                                     <StoreInfo>
//                                         <h3>{coupon.storeName} {coupon.discountRate}%</h3>
//                                     </StoreInfo>
//                                     <CouponText>{coupon.content}</CouponText>
//                                     <ExpirationDate>
//                                         유효기간: {new Date(coupon.expirationDate).toLocaleDateString()}
//                                     </ExpirationDate>
//                                 </CouponContent>
//                             </CouponCard>
//                         ))}
//                     </CouponList>
//                 )}

//                 {selectedCoupon && barcode && (
//                     <CouponDetails>
//                         <h3>{selectedCoupon.couponName} 바코드</h3>
//                         <Barcode>
//                             <img src={`data:image/png;base64,${barcode}`} alt="Coupon Barcode" />
//                             <p>쿠폰 바코드</p>
//                         </Barcode>
//                     </CouponDetails>
//                 )}
//             </CouponsContainer>

//             <BottomNav />
//         </div>
//     );
// }

// const CouponsContainer = styled.div`
//   padding: 16px;
// `;

// const CouponList = styled.div`
//   margin-top: 16px;
// `;

// const CouponCard = styled.div`
//   background: linear-gradient(135deg, #f6f9fc, #e3efff);
//   padding: 20px;
//   border-radius: 12px;
//   margin-bottom: 16px;
//   box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
//   cursor: pointer;
//   transition: transform 0.3s ease, box-shadow 0.3s ease;
// `;

// const CouponContent = styled.div`
//   display: flex;
//   flex-direction: column;
//   justify-content: center;
//   align-items: flex-start;
// `;

// const StoreInfo = styled.div`
//   display: flex;
//   align-items: center;
//   margin-bottom: 8px;
// `;

// const CouponText = styled.p`
//   font-size: 14px;
//   color: #333;
//   margin: 10px 0;
// `;

// const ExpirationDate = styled.p`
//   font-size: 12px;
//   color: #ff6347;
//   margin-top: 5px;
// `;

// const CouponDetails = styled.div`
//   margin-top: 20px;
//   padding: 20px;
//   background-color: white;
//   border-radius: 8px;
//   box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
// `;

// const Barcode = styled.div`
//   margin-top: 16px;
//   text-align: center;
//   img {
//     max-width: 150px;
//     max-height: 150px;
//   }
// `;

// export default Coupons;



// // function Coupons() {
// //     const [coupons, setCoupons] = useState([]); // 쿠폰 데이터 상태
// //     const [loading, setLoading] = useState(true); // 로딩 상태
// //     const [error, setError] = useState(null); // 에러 상태
// //     const [selectedCoupon, setSelectedCoupon] = useState(null); // 선택된 쿠폰 상태
// //     const [barcode, setBarcode] = useState(null); // 바코드 상태

// //     useEffect(() => {
// //         // 더미 데이터로 API처럼 처리
// //         const fetchCoupons = async () => {
// //             try {
// //                 const response = couponsData; // 더미 데이터 사용
// //                 setCoupons(response);
// //             } catch (err) {
// //                 setError("쿠폰 데이터를 가져오는데 실패했습니다.");
// //             } finally {
// //                 setLoading(false);
// //             }
// //         };

// //         fetchCoupons(); // 컴포넌트가 마운트될 때 데이터 로드
// //     }, []);

// //     const handleCouponClick = async (coupon) => {
// //         try {
// //             // 쿠폰 클릭 시 쿠폰 ID, storeNo, userNo를 포함한 데이터 전송
// //             const requestDto = {
// //                 couponId: coupon.id, // 쿠폰 ID
// //                 storeNo: coupon.storeNo, // storeNo (쿠폰 데이터에 맞게 수정)
// //                 userNo: 1, // userNo (임의로 1로 설정, 실제로는 로그인한 유저의 ID로 대체해야 함)
// //             };

// //             const response = await axios.post("/api/coupon/use", requestDto); // API 요청

// //             // 응답에서 바코드 정보를 받아와 상태 업데이트
// //             setBarcode(response.data.barcode);
// //             setSelectedCoupon(coupon); // 선택된 쿠폰 업데이트
// //         } catch (err) {
// //             setError("쿠폰 데이터를 가져오는데 실패했습니다.");
// //         }
// //     };

// //     if (loading) {
// //         return <p>로딩 중...</p>;
// //     }

// //     if (error) {
// //         return <p>{error}</p>;
// //     }

// //     return (
// //         <div>
// //             <HeaderContainer />
// //             <MyPageHeader />

// //             <CouponsContainer>
// //                 {coupons.length === 0 ? (
// //                     <p>현재 보유한 쿠폰이 없습니다.</p>
// //                 ) : (
// //                     <CouponList>
// //                         {coupons.map((coupon, index) => (
// //                             <CouponCard
// //                                 key={index}
// //                                 onClick={() => handleCouponClick(coupon)} // 쿠폰 클릭 시 바코드 요청
// //                             >
// //                                 <CouponContent>
// //                                     <StoreInfo>
// //                                         <h3>{coupon.storeName} {coupon.discountRate}%</h3>
// //                                     </StoreInfo>
// //                                     <CouponText>{coupon.content}</CouponText>
// //                                     <ExpirationDate>
// //                                         유효기간: {new Date(coupon.expirationDate).toLocaleDateString()}
// //                                     </ExpirationDate>
// //                                 </CouponContent>
// //                             </CouponCard>
// //                         ))}
// //                     </CouponList>
// //                 )}

// //                 {selectedCoupon && barcode && (
// //                     <CouponDetails>
// //                         <h3>{selectedCoupon.couponName} 바코드</h3>
// //                         <Barcode>
// //                             <img
// //                                 src={`data:image/png;base64,${barcode}`} // base64 바코드 이미지 표시
// //                                 alt="Coupon Barcode"
// //                             />
// //                             <p>쿠폰 바코드</p>
// //                         </Barcode>
// //                     </CouponDetails>
// //                 )}
// //             </CouponsContainer>

// //             <BottomNav />
// //         </div>
// //     );
// // }

// // const CouponsContainer = styled.div`
// //   padding: 16px;
// // `;

// // const CouponList = styled.div`
// //   margin-top: 16px;
// // `;

// // const CouponCard = styled.div`
// //   background: linear-gradient(135deg, #f6f9fc, #e3efff);
// //   padding: 20px;
// //   border-radius: 12px;
// //   margin-bottom: 16px;
// //   box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
// //   cursor: pointer;
// //   transition: transform 0.3s ease, box-shadow 0.3s ease;
// // `;

// // const CouponContent = styled.div`
// //   display: flex;
// //   flex-direction: column;
// //   justify-content: center;
// //   align-items: flex-start;
// // `;

// // const StoreInfo = styled.div`
// //   display: flex;
// //   align-items: center;
// //   margin-bottom: 8px;
// // `;

// // const CouponText = styled.p`
// //   font-size: 14px;
// //   color: #333;
// //   margin: 10px 0;
// // `;

// // const ExpirationDate = styled.p`
// //   font-size: 12px;
// //   color: #ff6347;
// //   margin-top: 5px;
// // `;

// // const CouponDetails = styled.div`
// //   margin-top: 20px;
// //   padding: 20px;
// //   background-color: white;
// //   border-radius: 8px;
// //   box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
// // `;

// // const Barcode = styled.div`
// //   margin-top: 16px;
// //   text-align: center;
// //   img {
// //     max-width: 150px;
// //     max-height: 150px;
// //   }
// // `;

// // export default Coupons;
