import React, { useState, useEffect } from 'react';
import axios from 'axios';

const SearchNutritionScroll = () => {
    const apiUrl = process.env.REACT_APP_BACKEND_API_URL;

    // 상태 관리
    const [maxSugar, setMaxSugar] = useState(100);
    const [maxCal, setMaxCal] = useState(500);
    const [items, setItems] = useState([]);
    const [error, setError] = useState(null);

    // API 호출 함수
    const fetchItems = async (maxSugar, maxCal) => {
        try {
            const response = await axios.get(`${apiUrl}/api/items/search`, {
                params: {
                    minSugar: 0, // 항상 0으로 고정
                    maxSugar,
                    minCal: 0, // 항상 0으로 고정
                    maxCal,
                },
            });
            setItems(response.data); // 받아온 데이터를 상태에 저장
        } catch (err) {
            console.error('API 호출 중 오류 발생:', err);
            setError('데이터를 불러오는 중 오류가 발생했습니다.');
        }
    };

    // 값이 변경될 때마다 API 호출
    useEffect(() => {
        fetchItems(maxSugar, maxCal);
    }, [maxSugar, maxCal]);

    return (
        <div>
            <h1>영양분으로 검색하기</h1>

            {/* 스크롤바 UI */}
            <div>
                <label>
                    Sugar (Max): {maxSugar}
                    <input
                        type="range"
                        min="0"
                        max="100"
                        value={maxSugar}
                        onChange={(e) => setMaxSugar(Number(e.target.value))}
                    />
                </label><br/>
                <label>
                    Calories (Max): {maxCal}
                    <input
                        type="range"
                        min="0"
                        max="500"
                        value={maxCal}
                        onChange={(e) => setMaxCal(Number(e.target.value))}
                    />
                </label><br/>
            </div>

            {/* 데이터 출력 */}
            <div>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                {!error && items.length === 0 && <p>데이터가 없습니다.</p>}
                {!error && items.length > 0 && (
                    <ul>
                        {items.map((item) => (
                            <li key={item.itemId}>{item.itemName}</li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
};

export default SearchNutritionScroll;
