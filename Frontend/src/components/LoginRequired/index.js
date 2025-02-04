import React from 'react';
import { Navigate } from 'react-router-dom';

const RoleBasedRoute = ({ user, requiredRole, children }) => {
    if (!user) {
        // 로그인이 되어 있지 않은 경우
        alert('로그인이 필요합니다. 로그인 화면으로 연결합니다.');
        return <Navigate to="/login" replace />;
    }

    if (user.user_type !== requiredRole) {
        // 사용자 유형이 요구 조건과 맞지 않는 경우
        alert('접근 권한이 없습니다.');
        return <Navigate to="/forbidden" replace />;
    }

    return children;
};

export default RoleBasedRoute;
