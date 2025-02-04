import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";

function Chats() {
    return (
        <div>
            <HeaderContainer />
            <MyPageHeader/>
            <h2>채팅창 목록</h2>


            <BottomNav />
        </div>
    );
}

export default Chats;