import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import RegisterButtons from "../../components/RegisterButtons";
import { useState } from "react";
import styled from "styled-components";
import axios from "axios";

function OwnerSignUp() {
    // 상태 관리 (폼 데이터)
    const [formData, setFormData] = useState({
        userName: '',
        userEmail: '',
        storeName: '',
        storeAddress: '',
        phoneNumber: '',
        businessNumber: '',
        storePic: null,
        isFlimarketAllowed: false  // 플리마켓 허용 여부 기본값 false
    });

    // 오류 메시지 상태 관리
    const [errors, setErrors] = useState({
        userName: '',
        userEmail: '',
        storeName: '',
        storeAddress: '',
        phoneNumber: '',
        businessNumber: '',
    });

    // 입력값 처리 함수
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    // 플리마켓 허용 여부 처리 함수
    const handleCheckboxChange = () => {
        setFormData((prevData) => ({
            ...prevData,
            isFlimarketAllowed: !prevData.isFlimarketAllowed,  // 체크 상태에 따라 true/false 값 변경
        }));
    };

    // 실시간 유효성 검사 함수
    const validateField = (name, value) => {
        let formErrors = { ...errors };

        switch (name) {
            case "phoneNumber":
                const phoneRegex = /^\d{3}-\d{4}-\d{4}$/;
                formErrors.phoneNumber = !value || !phoneRegex.test(value)
                    ? "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)"
                    : "";
                break;
            case "businessNumber":
                const businessNumberRegex = /^\d{10}$/;
                formErrors.businessNumber = !value || !businessNumberRegex.test(value)
                    ? "사업자등록번호는 숫자 10자리로 입력해주세요."
                    : "";
                break;
            case "storeAddress":
                formErrors.storeAddress = !value ? "매장 주소는 필수 입력입니다." : "";
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
                const formDataToSend = new FormData();
                Object.keys(formData).forEach(key => {
                    formDataToSend.append(key, formData[key]);
                    console.log(formData);
                });

                // POST 요청
                const response = await axios.post('http://i12a506.p.ssafy.io:8000/api/users/store-owner', formDataToSend);
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
                    <label>가게명 <span>*</span></label>
                    <input
                        type="text"
                        name="storeName"
                        value={formData.storeName}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        required
                        style={{ borderColor: errors.storeName ? 'red' : '#ccc' }}
                    />
                    {errors.storeName && <ErrorMessage>{errors.storeName}</ErrorMessage>}
                </InputGroup>

                <InputGroup>
                    <label>매장 주소 <span>*</span></label>
                    <input
                        type="text"
                        name="storeAddress"
                        value={formData.storeAddress}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        placeholder="예: 서울시 강남구 역삼동 123-45"
                        required
                        style={{ borderColor: errors.storeAddress ? 'red' : '#ccc' }}
                    />
                    {errors.storeAddress && <ErrorMessage>{errors.storeAddress}</ErrorMessage>}
                </InputGroup>

                <InputGroup>
                    <label>전화번호 <span>*</span></label>
                    <input
                        type="tel"
                        name="phoneNumber"
                        value={formData.phoneNumber}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        placeholder="숫자만 입력해주세요"
                        required
                        style={{ borderColor: errors.phoneNumber ? 'red' : '#ccc' }}
                    />
                    {errors.phoneNumber && <ErrorMessage>{errors.phoneNumber}</ErrorMessage>}
                </InputGroup>

                <InputGroup>
                    <label>사업자등록번호 <span>*</span></label>
                    <input
                        type="text"
                        name="businessNumber"
                        value={formData.businessNumber}
                        onChange={handleChange}
                        onBlur={handleBlur}
                        placeholder="예: 123-45-67890"
                        required
                        style={{ borderColor: errors.businessNumber ? 'red' : '#ccc' }}
                    />
                    {errors.businessNumber && <ErrorMessage>{errors.businessNumber}</ErrorMessage>}
                </InputGroup>


                {/* 매장 사진 업로드 */}
                <InputGroup>
                    <label>매장 사진</label>
                    <input
                        type="file"
                        accept="image/*"
                        onChange={(e) => handleChange(e)}
                    />
                </InputGroup>
                
                {/* 플리마켓 허용여부 체크박스 */}
                    <CheckboxWrapper>
                    <label>플리마켓 허용여부</label>
                    <input
                        type="checkbox"
                        checked={formData.isFlimarketAllowed}
                        onChange={handleCheckboxChange}
                    />
                    </CheckboxWrapper>

                <RegisterButtons />
            </form>
            <BottomNav />
        </div>
    );
}

const InputGroup = styled.div`
  margin-top: 15px;
  label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
    span {
      color: red;
      font-size: 18px;
    }
  }

  input {
    width: 85%;
    padding: 9px;
    font-size: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    &:focus {
      border-color: #007bff;
    }
  }
`;

const CheckboxWrapper = styled.div`
  display: flex;
  align-items: center;
  font-size: 15px;
  font-weight: bold;
  margin: 20px 0 7px 25px;

`;

const ErrorMessage = styled.div`
  color: red;
  font-size: 12px;
  margin-top: 5px;
`;

export default OwnerSignUp;
