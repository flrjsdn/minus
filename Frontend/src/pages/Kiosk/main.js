import "./main.css"
import logoimage from "./images/kiosk_logo.png";
import kmainnavbar from "./images/kmainNavbar.png"

import CartPage from "../../components/CartPage/CartPage";

function KioskMainScreen() {
    return (
        <div className="Kiosk">
            {/* Header Section */}

            <div className="kiosk_header">
                <img src={logoimage} alt="kiosk-logo"/>
            </div>

            <div className="kmain_navbar">
                <img src={kmainnavbar} alt="kmainnavbar"/>
            </div>

            <div><CartPage /></div>
        </div>)
            }

export default KioskMainScreen