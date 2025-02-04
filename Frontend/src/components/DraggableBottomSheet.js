import React, { useState, useRef } from "react";
import "./DraggableBottomSheet.css"; // 스타일 파일 추가

const DraggableBottomSheet = () => {
  const [panelHeight, setPanelHeight] = useState(40); // 기본 40% 높이
  const [startY, setStartY] = useState(0);
  const [isDragging, setIsDragging] = useState(false);
  const panelRef = useRef(null);

  const NAV_HEIGHT = 50; // ✅ 바텀 네비게이션 높이 설정
  const PANEL_WIDTH = "100%"; // ✅ 바텀 네비와 동일한 너비로 설정

  // 드래그 시작
  const handleTouchStart = (e) => {
    setStartY(e.touches[0].clientY);
    setIsDragging(true);
  };

  // 드래그 중 (위아래 이동)
  const handleTouchMove = (e) => {
    if (!isDragging) return;
    const deltaY = startY - e.touches[0].clientY;
    let newHeight = panelHeight + (deltaY / window.innerHeight) * 100;

    // 최소 높이: 네비게이션 위 (20%), 최대: 전체 화면의 85%
    if (newHeight < 20) newHeight = 20;
    if (newHeight > 85) newHeight = 85;

    setPanelHeight(newHeight);
  };

  // 드래그 종료 (자동 위치 조절)
  const handleTouchEnd = () => {
    setIsDragging(false);
    if (panelHeight > 70) setPanelHeight(85);
    else if (panelHeight < 30) setPanelHeight(20);
    else setPanelHeight(40);
  };

  return (
    <div
      ref={panelRef}
      className="bottom-sheet"
      style={{ height: `${panelHeight}%`, bottom: `${NAV_HEIGHT}px`,width: `${PANEL_WIDTH}`}} // ✅ 네비게이션 위로 조정
    >
      {/* 드래그 핸들 */}
      <div
        className="drag-handle"
        onTouchStart={handleTouchStart}
        onTouchMove={handleTouchMove}
        onTouchEnd={handleTouchEnd}
      ></div>

      {/* 바텀 시트 내용 */}
      <div className="bottom-sheet-content">
        <h2>매장 리스트</h2>
      </div>
    </div>
  );
};

export default DraggableBottomSheet;
