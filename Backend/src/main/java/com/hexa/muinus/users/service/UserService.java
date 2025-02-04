package com.hexa.muinus.users.service;

import com.hexa.muinus.common.jwt.JwtProvider;
import com.hexa.muinus.store.domain.store.Store;
import com.hexa.muinus.store.domain.store.repository.StoreRepository;
import com.hexa.muinus.users.domain.user.Users;
import com.hexa.muinus.users.domain.user.repository.UserRepository;
import com.hexa.muinus.users.dto.ConsumerRegisterRequestDto;
import com.hexa.muinus.users.dto.ReissueAccessTokenRequestDto;
import com.hexa.muinus.users.dto.StoreOwnerRegisterRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public Users registerConsumer(ConsumerRegisterRequestDto requestDto, HttpServletResponse response) {
        // 이미 가입한 회원인지 이메일로 확인
        isEmailDuplicated(requestDto.getUserEmail());

        // 신규 사용자(소비자) 생성
        Users user = Users.createConsumer(requestDto);

        // 회원 가입 시 토큰 발급
        jwtProvider.issueTokens(user, response);

        return userRepository.save(user);
    }

    /**
     * 점주 회원가입 시 정보 받아와 Users 테이블에 저장, 또한 Store 테이블에도 저장
     */
    @Transactional
    public Store registerStoreOwner(StoreOwnerRegisterRequestDto requestDto, HttpServletResponse response) {
        // 이미 가입한 회원인지 이메일로 확인
        isEmailDuplicated(requestDto.getUserEmail());

        // 신규 사용자(점주) 생성
        Users user = Users.createStoreOwner(requestDto);

        // 회원 가입 시 토큰 발급
        jwtProvider.issueTokens(user, response);

        Users savedUser = userRepository.save(user);
        // 신규 매장 생성
        Store store = Store.create(savedUser, requestDto);

        return storeRepository.save(store);
    }

    /**
     * refresh token 유효성 검증 후 access token 재발급
     */
    @Transactional
    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, ReissueAccessTokenRequestDto requestDto) {
        String refreshToken = jwtProvider.getCookieValue(request, "RefreshToken");

        // RefreshToken 유효한지 확인
        if (refreshToken != null && !refreshToken.isEmpty() && jwtProvider.validateToken(refreshToken)) {
            // DB에 저장된 refresh 토큰과 일치하는지 확인 후 AccessToken 재발급
            Users user = findUserByEmail(requestDto.getUserEmail());
            if (refreshToken.equals(user.getEmail())) {
                String accessToken = jwtProvider.createAccessToken(user);
                jwtProvider.setAccessTokensInCookie(response, accessToken);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 이메일 중복 검사
     */
    @Transactional(readOnly = true)
    public void isEmailDuplicated(String email) {
        if (userRepository.existsByEmail(email)) {
            log.info("User already exists with email {}", email);
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Transactional
    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
