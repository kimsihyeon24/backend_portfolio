package com.example.demo.dto;

import com.example.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final User user; // 우리가 만든 User 엔티티를 포함

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // 1. 유저의 권한을 반환 (시큐리티가 가장 중요하게 보는 곳!)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> {
            // ROLE_ 접두사를 붙여서 반환하는 것이 시큐리티의 관례입니다.
            return user.getRole();
        });
        return collection;
    }

    // 2. 유저의 비밀번호 반환
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 3. 유저의 아이디(로그인용) 반환
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 아래 설정들은 일단 모두 true(만료 안됨, 잠기지 않음)로 설정합니다.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}