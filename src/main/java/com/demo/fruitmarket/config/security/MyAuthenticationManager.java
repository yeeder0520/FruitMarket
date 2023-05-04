package com.demo.fruitmarket.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyAuthenticationManager implements AuthenticationManager {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public MyAuthenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        System.out.println("Start authenticate = " + authentication);
        String username = authentication.getName();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        /*驗證密碼正確性
        參數1:使用者代帶入密碼
        參數2:DB密碼 (加密過) */
        boolean matches = passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword());

        if (!matches) {
            throw new BadCredentialsException("密碼錯誤啦");
        }

        // 驗證通過，返回一個已驗證的身份信息對象
        return authentication;
    }
}
