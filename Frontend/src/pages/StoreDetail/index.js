import React, { useState, useEffect } from "react";
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import StoreDetailbar from "../../components/StoreDetailBar";
import StoreDetailApi from "../../api/StoreDetailApi";
import "./style.css";

const StoreDetail = () => {
    const [productData, setProductData] = useState(null); // API 데이터를 저장할 상태
    const [activeTab, setActiveTab] = useState("productInfo"); // 활성화된 탭 상태

    const handleTabClick = (tab) => {
        setActiveTab(tab); // 활성화된 탭 업데이트
    };

    useEffect(() => {
        // setProductData를 StoreDetailApi에 전달
        StoreDetailApi(setProductData);
    }, []); // 빈 배열을 의존성으로 전달하여 한 번만 실행되도록 설정

    useEffect(() => {
        console.log("Product Data:", productData);
    }, [productData]); // productData가 업데이트될 때마다 실행

    return (
        <div className="searchpagedom">
            <div className="storedetailheadercontainer"><HeaderContainer /></div>
            <div className="storedetailphotoinfo">
                <div className="market-image">
                    <img src={productData?.store?.storeImageUrl} alt="StoreImage"/>
                </div>
                <div className="market-detail">
                    <div className="market-name">{productData?.store.name}</div>
                    <div className="market-location">{productData?.store.address}</div>
                </div>
            </div>
            <div className="storedetailnavbar">
                {/* StoreDetailbar에 콜백 전달 */}
                <StoreDetailbar onTabClick={handleTabClick} />
            </div>
            <div className="storedetailmarketinfo">
                {/* 조건부 렌더링 */}
                {activeTab === "storeItems" && (
                    <ul className="storedetailul">
                        {productData?.storeItems?.map((storeItem) => (
                            <li key={storeItem.itemId} className="storedetailmarketli">
                                <img src={storeItem.itemImageUrl} alt="storedetailitemimage" />
                                {storeItem.itemName}
                                {storeItem.finalPrice}
                            </li>
                        ))}
                    </ul>
            )}
            {activeTab === "fliItems" && (
                <div>
                    <ul className="storedetailul">
                        {productData?.fliItems?.map((fliItem) => (
                            <li key={fliItem.fliItemId} className="storedetailmarketli">
                                <img src={fliItem.imagePath} alt="storedetailitemimage"/>
                                {fliItem.fliItemName}
                                {fliItem.Price}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
                {activeTab === "announcements" && (
                    <div>
                        <ul className="storedetailul">
                            {productData?.announcements?.map((announcement) => (
                                <li key={announcement.boardId} className="storedetailmarketli">
                                    {announcement.title}
                                    {announcement.createdAt}
                                </li>
                            ))}
                        </ul>
                    </div>
                )}
            </div>
        </div>
    );
};

export default StoreDetail