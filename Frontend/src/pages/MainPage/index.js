import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import SearchBar from "../../components/SearchBar/SearchBar";
import KakaoMapBackground from "../../components/KakaoMapBackground";
import DraggableBottomSheet from "../../components/DraggableBottomSheet/DraggableBottomSheet";
import BottomNav from "../../components/BottomNav/BottomNav";
import './style.css'


const MainPage = () => {
    return (
        <div className ="mainpage">
            <div className="mainpagecontents">
                <div className="mainpageheader"><HeaderContainer /></div>
                <div className="mainpagesearchbar"><SearchBar /></div>

                <div className="mainpagebottomsheet"><DraggableBottomSheet/></div>
                <div className="mainpagebottomnav"><BottomNav/></div>
            </div>
            <div className="mainpagekakaobackground"><KakaoMapBackground /></div>
        </div>
    );
}


export default MainPage;