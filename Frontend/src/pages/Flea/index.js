import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";

function Flea() {
    return (
        <div>
            <HeaderContainer />
            <MyPageHeader/>
            <h2>플리마켓 페이지</h2>
            <p>여기서 점주가 플리마켓을 관리할 수 있습니다.</p>

            <BottomNav />
        </div>
    );
}

export default Flea;