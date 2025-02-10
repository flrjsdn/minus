import React, { useRef, useState, useEffect } from "react";
import axios from "axios";
import styled from "styled-components";
import HeaderContainer from "../../components/HeaderContainer/HeaderContainer";
import BottomNav from "../../components/BottomNav/BottomNav";
import MyPageHeader from "../../components/MyPageHeader";

function Notice({ storeNo }) {
    const [notices, setNotices] = useState([]); // 공지사항을 상태로 관리
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [noticeTitle, setNoticeTitle] = useState("");
    const [noticeContent, setNoticeContent] = useState("");
    const [editNoticeId, setEditNoticeId] = useState(null); // 수정할 공지사항의 ID
    const modalBackground = useRef();
    const email = 'asf@fad.com'
    // storeNo는 변경되지 않기 때문에 useEffect에서 한 번만 사용
    useEffect(() => {
        const fetchNotices = async () => {
            try {
                const response = await axios.get(`http://i12a506.p.ssafy.io:8000/api/store/detail?email=${email}`);
                setNotices(response.data.announcements); // 공지사항 목록만 추출
            } catch (error) {
                console.error('공지사항 목록 가져오기 실패:', error);
            }
        };
        fetchNotices();
    }, []); 

    // 공지사항 등록
    const handleAddNotice = async () => {
        try {
            const response = await axios.post('http://i12a506.p.ssafy.io:8000/api/store/board', {
                storeNo: 39, 
                userNo: 1,
                title: noticeTitle,
                content: noticeContent,
                boardImageUrl: null, // 이미지 URL은 임시로 null
                userEmail: "asf@fad.com",
            });

            if (response.status === 200) {
                alert('공지사항이 등록되었습니다.');
                setNoticeTitle(""); // 제목 초기화
                setNoticeContent(""); // 내용 초기화
                setModalIsOpen(false); // 모달 닫기

                // 공지사항 목록 갱신
                setNotices((prevNotices) => [...prevNotices, response.data]);
            }
        } catch (error) {
            console.error('공지사항 등록 실패:', error.response?.data || error.message);
            alert('공지사항 등록에 실패했습니다.');
        }
    };

    // 공지사항 수정
    const handleEditNotice = async () => {
        try {
            const response = await axios.put('http://i12a506.p.ssafy.io:8000/api/store/board', {
                boardId: editNoticeId,
                title: noticeTitle,
                content: noticeContent,
            });

            if (response.status === 200) {
                alert('공지사항이 수정되었습니다.');
                setNoticeTitle(""); // 제목 초기화
                setNoticeContent(""); // 내용 초기화
                setModalIsOpen(false); // 모달 닫기

                // 수정된 공지사항 목록 갱신
                setNotices((prevNotices) => 
                    prevNotices.map((notice) =>
                        notice.boardId === editNoticeId
                            ? { ...notice, title: noticeTitle, content: noticeContent }
                            : notice
                    )
                );
            }
        } catch (error) {
            console.error('공지사항 수정 실패:', error.response?.data || error.message);
            alert('공지사항 수정에 실패했습니다.');
        }
    };

    // 수정 버튼 클릭 시 모달에 기존 공지사항 제목, 내용 설정
    const handleEditClick = (notice) => {
        setNoticeTitle(notice.title);
        setNoticeContent(notice.content);
        setEditNoticeId(notice.boardId);
        setModalIsOpen(true);
    };

    return (
        <Container>
            <HeaderContainer />
            <MyPageHeader />
            <Title>공지사항 목록</Title>

            <NoticeList>
                {notices.map((notice, index) => (
                    <NoticeItem key={`${notice.boardId}-${index}`}>
                        <h3>{notice.title}</h3>
                        <p>{notice.content}</p>
                        {notice.boardImageUrl && <img src={notice.boardImageUrl} alt="공지사항 이미지" />}
                        <small>{new Date(notice.createdAt).toLocaleString()}</small>
                        <EditButton onClick={() => handleEditClick(notice)}>수정</EditButton>
                    </NoticeItem>
                ))}
            </NoticeList>

            {/* 공지 등록 버튼 */}
            <Button onClick={() => setModalIsOpen(true)}>공지사항 작성하기</Button>

            {/* 모달 창 */}
            {modalIsOpen && (
                <ModalBackground
                    ref={modalBackground}
                    onClick={(e) => {
                        if (e.target === modalBackground.current) {
                            setModalIsOpen(false);
                        }
                    }}
                >
                    <ModalContent>
                        <h2>{editNoticeId ? '공지 수정' : '공지 등록'}</h2>
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
                        <ButtonContainer>
                            <CloseButton onClick={() => setModalIsOpen(false)}>닫기</CloseButton>
                            <Button onClick={editNoticeId ? handleEditNotice : handleAddNotice}>
                                {editNoticeId ? '수정' : '등록'}
                            </Button>
                        </ButtonContainer>
                    </ModalContent>
                </ModalBackground>
            )}

            <BottomNav />
        </Container>
    );
}

export default Notice;

// Styled Components
const Title = styled.h2`
    margin: 12px;
`;

const NoticeList = styled.ul`
    list-style: none;
    padding: 5px;
    margin: 0;
`;

const NoticeItem = styled.li`
    border: 1px solid #ddd;
    margin: 1px 5px 15px 5px;
`;

const Container = styled.div`
    background-color: #f8f9fa;
`;

const ModalBackground = styled.div`
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    background: rgba(0, 0, 0, 0.5);
`;

const ModalContent = styled.div`
    background-color: #ffffff;
    width: 80%;
    height: 70%;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
    text-align: center;
`;

const Input = styled.input`
    width: 90%;
    padding: 10px;
    margin-top: 10px;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 8px;
    outline: none;
    transition: border-color 0.3s ease;

    &:focus {
        border-color: #3f72af;
    }
`;

const TextArea = styled.textarea`
    width: 90%;
    padding: 12px;
    font-size: 1rem;
    margin-top: 10px;
    border: 1px solid #ccc;
    border-radius: 8px;
    min-height: 300px;
    outline: none;
    transition: border-color 0.3s ease;

    &:focus {
        border-color: #3f72af;
    }
`;

const ButtonContainer = styled.div`
    display: flex;
    justify-content: space-around;
    margin-top: 30px;
`;

const Button = styled.button`
    padding: 10px 20px;
    font-size: 1rem;
    background-color: #3f72af;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.3s ease;

    &:hover {
        background-color: #3f72af;
    }
`;

const CloseButton = styled(Button)`
    background-color: rgb(82, 80, 80);

    &:hover {
        background-color: rgb(57, 57, 57);
    }
`;

const EditButton = styled(Button)`
    background-color: #f0ad4e;

    &:hover {
        background-color: #f39c12;
    }
`;

// function Notice() {
//     const [notices, setNotices] = useState(noticesData); // 더미데이터를 상태로 관리
//     const [modalIsOpen, setModalIsOpen] = useState(false);
//     const [noticeTitle, setNoticeTitle] = useState("");
//     const [noticeContent, setNoticeContent] = useState("");
//     const modalBackground = useRef();

//     // 공지사항 등록
//     const handleAddNotice = async () => {
//         try {
//             const response = await axios.post('http://i12a506.p.ssafy.io:8000/api/store/board', {
//                 title: noticeTitle,
//                 content: noticeContent,
//                 boardImageUrl: null,
//                 userEmail: "jun9048@naver.com", // 임시 이메일 설정
//             });

//             if (response.status === 200) {
//                 alert('공지사항이 등록되었습니다.');
//                 setNoticeTitle("");
//                 setNoticeContent("");
//                 setModalIsOpen(false); // 등록 후 모달 닫기
//             }
//         } catch (error) {
//             console.error('공지사항 등록 실패:',  error.response?.data || error.message);
//             console.log('Field Errors:', error.response?.data?.fieldErrors);

//             alert('등록에 실패했습니다.');
//         }
//     };

//     return (
//         <Container>
//             <HeaderContainer />
//             <MyPageHeader />
//             <Title>공지 등록</Title>

//             <NoticeList>
//                 {notices.map((notice, index) => (
//                     <NoticeItem key={`${notice.storeNo}-${index}`}> 
//                         <h3>{notice.title}</h3>
//                         <p>{notice.content}</p>
//                         <img src={notice.boardImageUrl} alt="공지사항 이미지" />
//                         <small>{new Date(notice.date).toLocaleString()}</small>
//                     </NoticeItem>
//                 ))}
//             </NoticeList>


//             {/* 공지 등록 버튼 */}
//             <Button onClick={() => setModalIsOpen(true)}>공지사항 작성하기</Button>

//             {/* 모달 창 */}
//             {modalIsOpen && (
//                 <ModalBackground
//                     ref={modalBackground}
//                     onClick={(e) => {
//                         if (e.target === modalBackground.current) {
//                             setModalIsOpen(false);
//                         }
//                     }}
//                 >
//                     <ModalContent>
//                         <h2>공지 등록</h2>
//                         <Input
//                             type="text"
//                             placeholder="공지 제목"
//                             value={noticeTitle}
//                             onChange={(e) => setNoticeTitle(e.target.value)}
//                         />
//                         <TextArea
//                             placeholder="공지 내용"
//                             value={noticeContent}
//                             onChange={(e) => setNoticeContent(e.target.value)}
//                         />
//                         <ButtonContainer>
//                             <CloseButton onClick={() => setModalIsOpen(false)}>닫기</CloseButton>
//                             <Button onClick={handleAddNotice}>등록</Button>
//                         </ButtonContainer>
//                     </ModalContent>
//                 </ModalBackground>
//             )}

//             <BottomNav />
//         </Container>
//     );
// }

// export default Notice;

// const Title = styled.h2`
//     margin: 12px;
// `;

// const NoticeList = styled.ul`
//     list-style: none;  /* 목록 앞 점 제거 */
//     padding: 5px;
//     margin: 0;
// `;

// const NoticeItem = styled.li`
//     border: 1px solid #ddd;
//     margin: 1px 5px 15px 5px;


// `;

// const Container = styled.div`
//     background-color: #f8f9fa;
// `;

// const ModalBackground = styled.div`
//     width: 100%;
//     height: 100%;
//     position: fixed;
//     top: 0;
//     left: 0;
//     display: flex;
//     justify-content: center;
//     align-items: center;
//     background: rgba(0, 0, 0, 0.5);
// `;

// const ModalContent = styled.div`
//     background-color: #ffffff;
//     width: 80%;
//     height: 70%;
//     padding: 20px;
//     border-radius: 12px;
//     box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
//     text-align: center;
// `;

// const Input = styled.input`
//     width: 90%;
//     padding: 10px;
//     margin-top: 10px;
//     font-size: 1rem;
//     border: 1px solid #ccc;
//     border-radius: 8px;
//     outline: none;
//     transition: border-color 0.3s ease;

//     &:focus {
//         border-color: #3f72af;
//     }
// `;

// const TextArea = styled.textarea`
//     width: 90%;
//     padding: 12px;
//     font-size: 1rem;
//     margin-top: 10px;
//     border: 1px solid #ccc;
//     border-radius: 8px;
//     min-height: 300px;
//     outline: none;
//     transition: border-color 0.3s ease;

//     &:focus {
//         border-color: #3f72af;
//     }
// `;

// const ButtonContainer = styled.div`
//     display: flex;
//     justify-content: space-around;
//     margin-top: 30px;
// `;

// const Button = styled.button`
//     padding: 10px 20px;
//     font-size: 1rem;
//     background-color: #3f72af;
//     color: white;
//     border: none;
//     border-radius: 8px;
//     cursor: pointer;
//     transition: background-color 0.3s ease;

//     &:hover {
//         background-color: #3f72af;
//     }
// `;

// const CloseButton = styled(Button)`
//     background-color:rgb(82, 80, 80);

//     &:hover {
//         background-color:rgb(57, 57, 57);
//     }
// `;





