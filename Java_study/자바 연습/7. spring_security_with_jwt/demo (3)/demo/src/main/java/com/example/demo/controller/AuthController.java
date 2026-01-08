package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입은 누구나 접근 가능해야 하므로 /signup 변경 (SecurityConfig와 맞춰야 함)
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return authService.signUp(user);
    }

    // 로그인 로직은 LoginFilter가 처리하므로 컨트롤러에는 작성하지 않습니다.
    // 로그아웃은 SecurityConfig의 기본 logout 설정을 따릅니다.
}