package com.demo.fruitmarket.config.security;

import com.demo.fruitmarket.entity.UsersPO;
import com.demo.fruitmarket.repository.UsersRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyAuthenticationManager implements AuthenticationManager {

    private final MyUserDetailsService userDetailsService;

    public MyAuthenticationManager(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        System.out.println("Start authenticate = " + authentication);
        String username = authentication.getName();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!userDetails.getPassword().equals(authentication.getCredentials())) {
            System.out.println("密碼錯誤");
            throw new BadCredentialsException("Incorrect password");
        }
        // 將 authentication 存入 ThreadLocal，方便後續取得用戶訊息
        authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
        // 驗證通過，返回一個已驗證的身份信息對象
        return authentication;
    }
}
