import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import RegisterButtons from "../../components/RegisterButtons";
import { useState } from "react";
import styled from "styled-components";
import axios from "axios";

function UserSignUp() {
    // 상태 관리 (폼 데이터)
    const [formData, setFormData] = useState({
        userName: '',
        userEmail: '',
        userTelephone: '',
        userBirth: '',
    });

    // 오류 메시지 상태 관리
    const [errors, setErrors] = useState({
        userName: '',
        userEmail: '',
        userTelephone: '',
        userBirth: '',
    });

    // 입력값 처리 함수
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    // 실시간 유효성 검사 함수
    const validateField = (name, value) => {
        let formErrors = { ...errors };

        switch (name) {
            case "phoneNumber":
                const phoneRegex = /^\d{3}-\d{4}-\d{4}$/;
                formErrors.userTelephone = !value || !phoneRegex.test(value)
                    ? "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)"
                    : "";
                break;
            case "userBirth":
                const dateRegex = /^\d{4}-\d{2}-\d{2}$/;  // YYYY-MM-DD 형식 정규 표현식
                formErrors.bday = !value || !dateRegex.test(value)
                    ? "생년월일은 'YYYY-MM-DD' 형식으로 입력해주세요."
                    : "";
                break;
            default:
                break;
        }

        setErrors(formErrors);
    };

    // onBlur 이벤트를 통해 유효성 검사 실행
    const handleBlur = (e) => {
        const { name, value } = e.target;
        validateField(name, value);
    };

    // 폼 제출 처리 함수
    const handleSubmit = async (e) => {
        e.preventDefault();  // 기본 폼 제출 동작 방지

        // 유효성 검사
        const formErrors = { ...errors };
        Object.keys(formData).forEach((key) => {
            validateField(key, formData[key]);
        });

        // 오류가 없으면 서버로 데이터 전송
        if (!Object.values(formErrors).some(error => error !== '')) {
            try {
                // 서버로 전송할 데이터 준비
                const response = await axios.post('http://i12a506.p.ssafy.io:8000/api/users/store-owner', formData);
                console.log('가입 성공:', response.data); // 서버 응답 확인
            } catch (error) {
                console.error('가입 실패:', error);
            }
        } else {
            console.log("입력값에 오류가 있습니다.");
        }
    };

    return (
        <div>
            <HeaderContainer />
            <form onSubmit={handleSubmit}>

            <InputGroup>
                    <label>이름 <span>*</span></label>
                    <input
                        type="text"
                        name="userName"
                        value={formData.userName}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        style={{ borderColor: errors.userName ? 'red' : '#ccc' }}
                    />
                    {errors.userName && <ErrorMessage>{errors.userName}</ErrorMessage>}
                </InputGroup>

                <InputGroup>
                    <label>이메일 <span>*</span></label>
                    <input
                        type="text"
                        name="userEmail"
                        value={formData.userEmail}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        style={{ borderColor: errors.userEmail ? 'red' : '#ccc' }}
                    />
                    {errors.userEmail && <ErrorMessage>{errors.userEmail}</ErrorMessage>}
                </InputGroup>
                
                <InputGroup>
                    <label>전화번호 <span>*</span></label>
                    <input
                        type="text"
                        name="userTelephone"
                        value={formData.userTelephone}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        placeholder="예: 010-1234-5678"
                        required
                        style={{ borderColor: errors.phoneNumber ? 'red' : '#ccc' }}
                    />
                    {errors.phoneNumber && <ErrorMessage>{errors.phoneNumber}</ErrorMessage>}
                </InputGroup>

                <InputGroup>
                    <label>생년월일 <span>*</span></label>
                    <input
                        type="date"  // 날짜 선택기를 사용
                        name="bday"
                        value={formData.bday}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        max="9999-12-31"  // 최대값을 9999-12-31로 설정
                        min="1900-01-01"  // 최소값을 1900-01-01로 설정
                        style={{ borderColor: errors.bday ? 'red' : '#ccc' }}
                    />
                    {errors.bday && <ErrorMessage>{errors.bday}</ErrorMessage>}
                </InputGroup>

                <RegisterButtons />
            </form>
            <BottomNav />
        </div>
    );
}

const InputGroup = styled.div`
  margin-bottom: 30px; /* 항목 간 간격 */
  margin-top: 40px;
  label {
    display: block;
    margin-bottom: 8px; /* label과 input 간 간격 */
    font-weight: bold;

    span {
      color: red; 
      font-size: 18px;
    }
  }

  input {
    width: 80%;
    padding: 8px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
    &:focus {
      border-color: #007bff;
    }
  }
`;

const ErrorMessage = styled.div`
  color: red;
  font-size: 12px;
  margin-top: 5px;
`;

export default UserSignUp;
