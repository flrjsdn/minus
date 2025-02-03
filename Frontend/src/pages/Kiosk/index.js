import { Link } from 'react-router-dom'
import "./index.css"
import logoimage from "./images/kiosk_logo.png"
import barcodeimage from "./images/barcode_icon.png"
import description from "./images/Frame 1.png"


function Kiosk() {
    return (
        <div className="Kiosk">
            {/* Header Section */}
            <h1 className="kiosk_header">
                <img src={logoimage} alt="Kiosk Logo" />
            </h1>

            {/* Barcode Image Section */}
            <h2 className="barcode_image">
                <img src={barcodeimage} alt="Barcode Logo" />
            </h2>

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
            <h3 className="description">
                <img src={description} alt="instruction" />
            </h3>
        </div>
    );
}

export default Kiosk;
