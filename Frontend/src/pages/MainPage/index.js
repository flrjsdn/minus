import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import SearchBar from "../../components/SearchBar/SearchBar";
// import Map from "../../components/Map/Map";
import DraggableBottomSheet from "../../components/DraggableBottomSheet/DraggableBottomSheet";
import BottomNav from "../../components/BottomNav/BottomNav";

function MainPage() {
    return (
        <div>
            <HeaderContainer /> 
            <SearchBar />
            <DraggableBottomSheet />
            <BottomNav />
        </div>
    );
}


export default MainPage;