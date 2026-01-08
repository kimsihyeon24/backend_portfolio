package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 추가

    public User signUp(User user) {
        // 1. 비밀번호 암호화 필수! (안 하면 로그인 필터에서 인식을 못함)
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // 2. 권한 설정 (사용자가 관리자 123인 경우만 ADMIN, 나머지는 USER)
        if (user.getUsername().equals("123")) {
            user.setRole("ROLE_ADMIN");
        } else {
            user.setRole("ROLE_USER");
        }

        return userRepository.save(user);
    }
}