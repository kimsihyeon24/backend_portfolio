package com.example.demo.util;

import com.example.demo.dto.CustomUserDetails;
import com.example.demo.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; // 정확한 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {



        // 1. 헤더에서 Authorization 추출
        String authorization = request.getHeader("Authorization");

        // 2. 토큰 검증 (null이거나 Bearer로 시작하지 않으면 패스)
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        // 3. 토큰 만료 체크
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4. 토큰에서 정보 추출 (JWTUtil에 구현한 메서드 사용)
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // 5. 시큐리티 전용 유저 객체 생성 (임시 비밀번호 사용)
        User user = new User();
        user.setId(username); // 또는 username 필드에 세팅
        user.setRole(role);
        user.setPassword("password"); // 필터에서는 실제 비번이 필요 없음

        // 6. CustomUserDetails 생성 (이 클래스도 만드셔야 합니다!)
         CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 7. 스프링 시큐리티 인증 토큰 생성
         Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 8. 세션에 사용자 등록 (핵심!)
         SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}