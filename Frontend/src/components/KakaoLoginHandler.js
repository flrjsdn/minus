import React, { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";


function KakaoLoginHandler() {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    // URL에서 code 파라미터 추출
    const queryParams = new URLSearchParams(location.search);
    const code = queryParams.get("code");

    if (code) {
      // 백엔드로 인가 코드 전달
      fetch("/api/kakao-login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ code }),
        credentials: "include",  // 쿠키를 포함하여 요청
      })
        .then(response => {
          if (response.ok) {
            // 로그인 성공시 메인 페이지로 리디렉션
            navigate("/");
          } else {
            // 로그인 실패시 처리
            navigate("/login");
          }
        })
        .catch(error => {
          console.error("Error:", error);
        });
    }
  }, [location, navigate]);

  return (
    <div>
      <h2>로그인 처리 중...</h2>
    </div>
  );
}

export default KakaoLoginHandler;
