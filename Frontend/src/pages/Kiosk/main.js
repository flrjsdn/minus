import "./main.css"
import logoimage from "./images/kiosk_logo.png";
import kmainnavbar from "./images/kmainNavbar.png"
import CartPage from "../../components/Kiosk/Cartpage";

function KioskMainScreen() {
    return (
        <div className="Kiosk">
            {/* Header Section */}
            <h1 className="kiosk_header">
                <img src={logoimage} alt="kiosk-logo"/>
            </h1>

            <h2 className="kmain_navbar">
                <img src={kmainnavbar} alt="kmainnavbar"/>
            </h2>

            <div><CartPage /></div>
        </div>)
            }

export default KioskMainScreen