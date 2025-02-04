import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";

function Notice() {
    return (
        <div>
            <HeaderContainer />
            <MyPageHeader/>
            <h2>공지사항</h2>
            <p>여기서 공지사항을 등록 및 수정할 수 있습니다.</p>

            <BottomNav />
        </div>
    );
}

export default Notice;