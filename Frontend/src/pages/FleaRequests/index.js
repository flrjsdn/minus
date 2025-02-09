import React, { useState, useEffect } from 'react';
import axios from 'axios';
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";

const FleaRequests = ({ storeId }) => {
  const [requests, setRequests] = useState([]); // 요청 목록을 배열로 저장

  useEffect(() => {
    const fetchFleaMarketRequests = async () => {
      try {
        // API 요청 URL 수정
        const response = await axios.get(`http://i12a506.p.ssafy.io:8000/api/fli/list?storeId=${storeId}`);
        setRequests(response.data); // 배열 형태로 데이터 저장
      } catch (error) {
        console.error("플리마켓 요청 목록 가져오기 실패", error);
      }
    };
    fetchFleaMarketRequests();
  }, [storeId]);

  return (
    <div>
      <HeaderContainer />
      <MyPageHeader />
      <h2>플리마켓 요청 목록</h2>
      {requests.length > 0 ? (
        requests.map((request, index) => ( // 요청 목록을 반복하여 출력
          <div key={index}>
            <p><strong>아이템 이름:</strong> {request.itemName}</p>
            <p><strong>수량:</strong> {request.quantity}</p>
            <p><strong>가격:</strong> {request.price}원</p>
            <p><strong>섹션 번호:</strong> {request.sectionNumber}</p>
            <p><strong>입고 날짜:</strong> {request.expirationDate}일</p>
            <p><strong>판매자 계좌 정보:</strong> {request.userAccount} ({request.userBank}, {request.accountName})</p>
            <hr />
          </div>
        ))
      ) : (
        <p>플리마켓 요청이 없습니다</p>
      )}
      <BottomNav />
    </div>
  );
};

export default FleaRequests;
