package com.hexa.muinus.users.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexa.muinus.common.jwt.JwtProvider;
import com.hexa.muinus.users.domain.user.Users;
import com.hexa.muinus.users.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String profileUrl;

    /**
     * 카카오로부터 액세스 토큰 발급
     * @param authorizationCode
     * @return
     */
    public String getAccessTokenFromKakao(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUrl);
        body.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, headers);

        // 카카오로 액세스 토큰 발급 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // 반환 값에서 액세스 토큰 추출
        ObjectMapper objectMapper = new ObjectMapper();
        String accessToken = "";
        try {
            accessToken = objectMapper.readTree(responseEntity.getBody()).get("access_token").asText();
        } catch (Exception e) {
            log.error("카카오 액세스 토큰 교환 오류 발생");
        }
        return accessToken;
    }

    /**
     * 카카오로부터 사용자 정보 받아오기
     * @param accessToken
     * @return
     */
    public String getUserKakaoProfile(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // 카카오에 사용자 정보를 요청하기 위해 필요한 설정
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> userKakaoProfileRequest = new HttpEntity<>(headers);

        // 사용자 정보 요청
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                profileUrl,
                HttpMethod.GET,
                userKakaoProfileRequest,
                String.class
        );

        // 카카오로부터 사용자 이메일 수신
        ObjectMapper objectMapper = new ObjectMapper();
        String userEmail = "";
        try {
            userEmail = objectMapper.readTree(responseEntity.getBody()).get("kakao_account").get("email").asText();
        } catch (Exception e) {
            log.error("사용자 이메일 정보 요청 실패");
        }
        return userEmail;
    }

    /**
     * 이메일 정보 조회로 회원가입한 사용자인지 확인
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true)
    public Users findUser(String userEmail) {
        Users user = userRepository.findByEmail(userEmail);
        // 사용자 존재 여부 확인
        if (user == null) {
            // 사용자 미존재 시 403 에러 반환
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return userRepository.findByEmail(userEmail);
    }
}
