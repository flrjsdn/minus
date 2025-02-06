import React from "react";
import "./style.css";

export const StoreDetailbar = ({ onTabClick }) => {
    return (
        <div className="box">
            {/* 각 버튼 클릭 시 onTabClick 호출 */}
            <button
                className='storedetailbarbutton1'
                onClick={() => onTabClick("storeItems")}
            >
                제품정보
            </button>
            <button
                className='storedetailbarbutton2'
                onClick={() => onTabClick("fliItems")}
            >
                플리마켓
            </button>
            <button
                className='storedetailbarbutton3'
                onClick={() => onTabClick("announcements")}
            >
                공지사항
            </button>
        </div>
    );
};

export default StoreDetailbar;
