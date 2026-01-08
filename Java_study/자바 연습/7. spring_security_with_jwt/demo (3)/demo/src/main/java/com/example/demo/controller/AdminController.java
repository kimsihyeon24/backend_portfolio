package com.example.demo.controller;

import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/check")
    public String checkAdmin() {
        return "관리자페이지 접근 됐습니다.!@!@";
    }

}
