package com.demo.fruitmarket.config.security;

import com.demo.fruitmarket.entity.UsersPO;
import com.demo.fruitmarket.repository.UsersRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyAuthenticationManager implements AuthenticationManager {
    private final UsersRepo usersRepo;

    public MyAuthenticationManager(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        System.out.println("Start authenticate = " + authentication);
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 根據請求中提供的身份信息進行身份驗證，例如從數據庫中獲取用戶信息
        UsersPO usersPO = usersRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));

        // 驗證通過，返回一個已驗證的身份信息對象
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }
}
