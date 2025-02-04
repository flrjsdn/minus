import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";

function MyPage() {
    return (
        <div>
            <HeaderContainer />
            <MyPageHeader/>
            <BottomNav />
        </div>
    );
}

export default MyPage;