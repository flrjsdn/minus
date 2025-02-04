import styled from "styled-components";
import { Link, useLocation } from "react-router-dom";

function MyPageHeader() {
    const location = useLocation();

    return (
        <Header>
            <Nav>
                <NavLink to="/mypage/edit" active={location.pathname === "/mypage/edit"}>정보수정</NavLink>
                <NavLink to="/mypage/chats" active={location.pathname === "/mypage/chats"}>채팅목록</NavLink>
                <NavLink to="/mypage/coupons" active={location.pathname === "/mypage/coupons"}>쿠폰함</NavLink>
            </Nav>
        </Header>
    );
}

const Header = styled.header`
  background: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 17px;
  display: flex;
  justify-content: center;
`;

const Nav = styled.nav`
  display: flex;
  gap: 70px;
`;

const NavLink = styled(Link)`
  color: ${({ active }) => (active ? "#3f72af" : "#000")};
  text-decoration: none;
  font-size: 1rem;
  font-weight: 500;

  &:hover {
    text-decoration: underline;
  }
`;

export default MyPageHeader;
