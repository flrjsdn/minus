import React from "react";
import HeaderContainer from "../../../components/HeaderContainer";
import StoreDetailBar from "../../../components/StoreDetailBar/StoreDetailBar"
import "./index.css"

function SearchPage() {


    return (
        <div>
            <HeaderContainer/>
            {/* 가게사진, 이름, 위치 api로 호출  */}
            <div className="box">
                <div className="market-image">사진</div>
                <div className="market-detail">
                    <br />
                    <div className="market-name">가게이름</div>
                    <br/>
                    <div className="market-location">위치</div>
                </div>
            </div>
            <StoreDetailBar/>
</div>
)
    ;
}


export default SearchPage;