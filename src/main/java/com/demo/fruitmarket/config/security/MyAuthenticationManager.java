package com.demo.fruitmarket.config.security;

import com.demo.fruitmarket.entity.UsersPO;
import com.demo.fruitmarket.repository.UsersRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationManager implements AuthenticationManager {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepo usersRepo;

    public MyAuthenticationManager( PasswordEncoder passwordEncoder, UsersRepo usersRepo) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepo = usersRepo;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        System.out.println("Start authenticate = " + authentication);
        String username = authentication.getName();

        UsersPO usersPO = usersRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + username));

        /*驗證密碼正確性
        參數1:使用者代帶入密碼
        參數2:DB密碼 (加密過) */
        boolean matches = passwordEncoder.matches(authentication.getCredentials().toString(), usersPO.getSecret());


        if (!matches) {
            throw new BadCredentialsException("Incorrect password");
        }

        // 驗證通過，返回一個已驗證的身份信息對象
        return authentication;
    }
}
