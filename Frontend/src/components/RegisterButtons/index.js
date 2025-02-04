import React from "react";
import { useNavigate } from 'react-router-dom';
import Button from "../Button";
import styled from "styled-components";

function RegisterButtons({ onSubmit }) {
    const navigate = useNavigate();
  
    const handleBack = () => {
      navigate(-1); // 뒤로 가기 (이전 페이지로 이동)
    };
  
    return (
      <ButtonContainer>
        <Button type="TERTIARY" onClick={handleBack}>
            뒤로가기
        </Button>
        
        <Button type="PRIMARY" onClick={onSubmit}>
            가입하기
        </Button>
        </ButtonContainer>
      );
  }
  
const ButtonContainer = styled.div`
display: flex;
justify-content: center; /* 버튼을 중앙 정렬 */
gap: 20px; /* 버튼 사이 간격 조절 */
`;

export default RegisterButtons;