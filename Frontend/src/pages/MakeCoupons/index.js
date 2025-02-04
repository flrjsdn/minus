import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";

function MakeCoupons() {
    return (
        <div>
            <HeaderContainer />
            <MyPageHeader/>
            <h2>쿠폰 등록 페이지지</h2>
            <p>여기서 점주가 쿠폰을 등록할 수 있습니다.</p>

            <BottomNav />
        </div>
    );
}

export default MakeCoupons;