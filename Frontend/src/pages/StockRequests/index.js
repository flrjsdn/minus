import React, { useState, useEffect } from 'react';
import axios from 'axios';
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";

const StockRequests = ({ storeNo }) => { //storeNo 를 가져와서 props로
  const [items, setItems] = useState([]); // 아이템들을 배열로 저장

  useEffect(() => {
    const fetchData = async () => {
      try {
        // 요청 URL을 직접 수정
        const response = await axios.get(`http://i12a506.p.ssafy.io:8000/api/item/regist_list?storeNo=${storeNo}`);
        setItems(response.data); // 배열로 저장
      } catch (error) {
        console.error("데이터 가져오기 실패", error);
      }
    };
    fetchData();
  }, [storeNo]);

  return (
    <div>
      <HeaderContainer />
      <MyPageHeader />
      <h2>상품 요청 목록</h2>
      {items.length > 0 ? ( // 아이템 배열이 비어있지 않으면 출력
        items.map((item, index) => ( // 아이템 배열을 반복하여 출력
          <p key={index}>
            {item.itemName}에 대한 요청 수: {item.requestCount}
          </p>
        ))
      ) : (
        <p>입고 요청 내역이 없습니다</p>
      )}
      <BottomNav />
    </div>
  );
};

export default StockRequests;
