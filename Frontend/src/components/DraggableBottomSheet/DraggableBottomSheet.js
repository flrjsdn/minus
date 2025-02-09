import React, { useState, useRef, useEffect } from "react";
import MainpageStoreDetailApi from "../../api/MainpageStorelistApi";
import "./DraggableBottomSheet.css";

const DraggableBottomSheet = ({ coords }) => {
  // api 호출 useState
  const [mainpageStorelist, setMainpageStorelist] = useState(() => {
    if (coords) {
      // coords가 기본값일 경우 API 호출로 초기 데이터 가져오기
      let initialData = null;
      MainpageStoreDetailApi({
        coords,
        receivedData: (data) => {
          initialData = data;
        },
      });
      return initialData;
    }
    return null; // coords가 없을 경우 null로 초기화
  });

  // 바텀시트 동작 useState
  const [panelHeight, setPanelHeight] = useState(10); // 기본 10% 높이
  const [startY, setStartY] = useState(0);
  const [isDragging, setIsDragging] = useState(false);
  const panelRef = useRef(null);

  const NAV_HEIGHT = 69; // ✅ 바텀 네비게이션 높이 설정
  const PANEL_WIDTH = "100%"; // ✅ 바텀 네비와 동일한 너비로 설정

  // 드래그 시작
  const handlePointerDown = (e) => {
    e.preventDefault(); // 기본 동작 방지 (예: 스크롤)
    const startY = e.clientY || e.touches?.[0]?.clientY;
    setStartY(startY);
    setIsDragging(true);
  };

  // 드래그 중 (위아래 이동)
  const handlePointerMove = (e) => {
    if (!isDragging) return;
    e.preventDefault(); // 기본 동작 방지
    const currentY = e.clientY || e.touches?.[0]?.clientY;
    const deltaY = startY - currentY;
    let newHeight = panelHeight + (deltaY / window.innerHeight) * 100;

    // 최소 높이: 네비게이션 위 (10%), 최대: 전체 화면의 85%
    if (newHeight < 10) newHeight = 10;
    if (newHeight > 85) newHeight = 50;

    setPanelHeight(newHeight);
  };

  // 드래그 종료 (자동 위치 조절)
  const handlePointerUp = () => {
    setIsDragging(false);
    if (panelHeight > 20) setPanelHeight(50); // 중간 위치로 스냅
    else setPanelHeight(10); // 최소 위치로 스냅
  };

  // API 호출: coords가 변경될 때마다 실행
  useEffect(() => {
    if (coords) {
      MainpageStoreDetailApi({
        coords,
        receivedData: setMainpageStorelist, // 데이터를 업데이트하는 콜백 함수 전달
      });
    }
  }, [coords]);

  return (
      <div
          ref={panelRef}
          className="bottom-sheet"
          style={{
            height: `${panelHeight}%`,
            bottom: `${NAV_HEIGHT}px`,
            width: `${PANEL_WIDTH}`,
          }} // ✅ 네비게이션 위로 조정
      >
        {/* 드래그 핸들 */}
        <div
            className="drag-handle"
            onPointerDown={handlePointerDown}
            onPointerMove={handlePointerMove}
            onPointerUp={handlePointerUp}
        ></div>

        {/* 바텀 시트 내용 */}
        <div className="bottom-sheet-content">
          <h2>매장 리스트</h2>
          <p>위도: {coords?.lat}</p>
          <p>경도: {coords?.lng}</p>
          {mainpageStorelist ? (
              <ul>
                {mainpageStorelist.map((store, index) => (
                    <li key={index}>{store.name}</li>
                ))}
              </ul>
          ) : (
              <p>데이터를 불러오는 중...</p>
          )}
        </div>
      </div>
  );
};

export default DraggableBottomSheet;
