package com.hexa.muinus.users.controller;

import com.hexa.muinus.common.jwt.JwtProvider;
import com.hexa.muinus.users.domain.user.Users;
import com.hexa.muinus.users.service.OauthService;
import com.hexa.muinus.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class OauthController {

    private final OauthService oauthService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @GetMapping("/login")
    public void kakaoLogin(HttpServletResponse response) {
        log.info("kakao login");
        oauthService.getAuthorizationCode(response);
    }

    @GetMapping("/kauth")
    public void kakaoLogin(@RequestParam("code") String authorizationCode, HttpServletResponse response) throws Exception {
        log.info("kakao login redirect for token");
        String accessToken = oauthService.getAccessTokenFromKakao(authorizationCode);
        log.info("123123");
        String userEmail = oauthService.getUserKakaoProfile(accessToken);
        log.info("456456");
        Users user = oauthService.findUser(userEmail, response);
        log.info("567567");
        jwtProvider.issueTokens(user, response);
        log.info("789789");
        oauthService.redirectToMainPage(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> kakaoLogout(HttpServletRequest request, HttpServletResponse response){
        String userEmail = jwtProvider.getUserEmailFromAccessToken(request);
        Users user = userService.findUserByEmail(userEmail);

        if(user != null){
            // 쿠키에서 토큰 삭제
            jwtProvider.clearTokens(response);

            // DB에서 refresh token 삭제
            oauthService.deleteRefreshToken(user);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<?> kakaoCheck(){
        oauthService.check();
        return ResponseEntity.ok().build();
    }

}
