import { Link } from 'react-router-dom'
import "./index.css"
import KioskHeaderContainer from "../../components/KioskHeaderContainer";


function Kiosk() {
    return (
        <div className="kiosk">

            <div className="headercontainer1">
                <KioskHeaderContainer/>
            </div>

            {/* Barcode Image Section */}
            <div className="barcode_image">
                <img src="/barcode_icon.png" alt="kioskbarcode"/>
            </div>

            {/* Instruction Section */}
            <section className="instructions">
                <p>상품은 <span className="highlight">바코드 스캐너</span>에 찍어주세요!</p>
                <p>플리마켓 제품은 <span className="highlight">키오스크</span>에서 구매하세요!</p>
            </section>

            {/* Start Button */}
            <Link to="/kmain">
                <button className="start_button">시작하기</button>
            </Link>
            
            {/* Description*/}
            <div className="description">
                <img src='/Description.png' alt= "kioskinstruction" />
            </div>
        </div>
    )
}

export default Kiosk
