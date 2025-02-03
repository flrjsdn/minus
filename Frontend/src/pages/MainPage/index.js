import HeaderContainer from "../../components/HeaderContainer";
import SearchBar from "../../components/SearchBar";
// import Map from "../../components/Map/Map";
import DraggableBottomSheet from "../../components/DraggableBottomSheet";
import BottomNav from "../../components/BottomNav";

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