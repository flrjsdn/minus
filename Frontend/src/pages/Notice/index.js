import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";
import React, { useState } from "react";
import { noticesData } from "../../dummydata/notice";

function Notice() {
    const [notices, setNotices] = useState(noticesData); // 더미데이터를 상태로 관리

    const handleAddNotice = () => {
        // 새로운 공지사항 추가
        const newNotice = {
            storeNo: notices.length + 1,
            userNo: 1,
            title: "새로운 공지사항",
            content: "새로운 공지사항 내용",
            boardImageUrl: "https://via.placeholder.com/150",
            date: new Date(),
        };

        // 상태에 새로운 공지사항을 추가
        setNotices((prevNotices) => [...prevNotices, newNotice]);
    };

    return (
        <div>
            <HeaderContainer />
            <MyPageHeader />
            <h2>공지사항</h2>
            <ul>
                {notices.map((notice) => (
                    <li key={notice.storeNo}>
                        <h3>{notice.title}</h3>
                        <p>{notice.content}</p>
                        <img src={notice.boardImageUrl} alt="공지사항 이미지" />
                        <small>{new Date(notice.date).toLocaleString()}</small>
                    </li>
                ))}
            </ul>
            <button onClick={handleAddNotice}>등록</button>
            <button>수정</button>
            <BottomNav />
        </div>
    );
}

export default Notice;
