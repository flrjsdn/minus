import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";

function Stock() {
    return (
        <div>
            <HeaderContainer />
            <MyPageHeader/>
            <h2>입고 요청 페이지지</h2>
            <p>여기서 점주가 입고 요청을 확인할 수 있습니다.</p>

            <BottomNav />
        </div>
    );
}

export default Stock;