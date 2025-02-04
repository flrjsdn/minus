import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";

function EditProfile() {
    return (
        <div>
            <HeaderContainer />
            <MyPageHeader/>
            <h2>정보 수정</h2>
            <p>여기서 회원 정보를 수정할 수 있습니다.</p>

            <BottomNav />
        </div>
    );
}

export default EditProfile;

