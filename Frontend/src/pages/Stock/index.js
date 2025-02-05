import React, { useState } from 'react';
import axios from 'axios';
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";
import styled from 'styled-components';

function Notice() {
    const [noticeTitle, setNoticeTitle] = useState("");
    const [noticeContent, setNoticeContent] = useState("");

    // 공지사항 등록
    const handleAddNotice = async () => {
        try {
            const response = await axios.post('http://i12a506.p.ssafy.io:8000/api/store/board', {
                title: noticeTitle,
                content: noticeContent,
            });

            if (response.status === 201) {
                alert('공지사항이 등록되었습니다.');
                setNoticeTitle("");
                setNoticeContent("");
            }
        } catch (error) {
            console.error('공지사항 등록 실패:', error);
            alert('등록에 실패했습니다.');
        }
    };

    // 공지사항 수정
    const handleEditNotice = async (noticeId) => {
        try {
            const response = await axios.put(`http://i12a506.p.ssafy.io:8000/api/store/board${noticeId}/`, {
                title: noticeTitle,
                content: noticeContent,
            });

            if (response.status === 200) {
                alert('공지사항이 수정되었습니다.');
                setNoticeTitle("");
                setNoticeContent("");
            }
        } catch (error) {
            console.error('공지사항 수정 실패:', error);
            alert('수정에 실패했습니다.');
        }
    };

    return (
        <Container>
            <HeaderContainer />
            <MyPageHeader />
            <Title>매장의 중요한 소식을 공지사항으로 등록해보세요!</Title>
            
            <FormContainer>
                <Input
                    type="text"
                    placeholder="공지 제목"
                    value={noticeTitle}
                    onChange={(e) => setNoticeTitle(e.target.value)}
                />
                <TextArea
                    placeholder="공지 내용"
                    value={noticeContent}
                    onChange={(e) => setNoticeContent(e.target.value)}
                />
            </FormContainer>

            <ButtonContainer>
                <Button onClick={handleAddNotice}>등록</Button>
                <Button onClick={() => handleEditNotice(1)}>수정</Button> {/* 수정할 공지사항의 ID 사용 */}
            </ButtonContainer>

            <BottomNav />
        </Container>
    );
}

const Container = styled.div`
    background-color: #f8f9fa;
`;

const Title = styled.p`
    font-size: 0.9rem;
    font-weight: 500;
    margin: 20px 0 20px 0;
`;

const FormContainer = styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin-bottom: 30px;
`;

const Input = styled.input`
    padding: 10px;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 8px;
    outline: none;

    margin: 10px;
    transition: border-color 0.3s ease;

    &:focus {
        border-color: #3f72af;
    }
`;

const TextArea = styled.textarea`
    padding: 12px;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 8px;
    outline: none;
    width: 80%;
    margin: 0 auto;
    min-height: 150px;
    transition: border-color 0.3s ease;

    &:focus {
        border-color: #3f72af;
    }
`;

const ButtonContainer = styled.div`
    display: flex;
    justify-content: center;
    gap: 15px;
`;

const Button = styled.button`
    padding: 10px 25px;
    font-size: 1rem;
    background-color: #3f72af;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.3s ease;

    &:hover {
        background-color: #36588b;
    }

    &:active {
        background-color: #314f79;
    }
`;

export default Notice;
