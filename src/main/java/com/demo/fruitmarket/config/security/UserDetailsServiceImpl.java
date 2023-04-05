package com.demo.fruitmarket.config.security;

import com.demo.fruitmarket.entity.UsersPO;
import com.demo.fruitmarket.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UsersPO usersPO = usersRepo.findByUsername(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

        return User.builder()
                .username(usersPO.getUsername()) //使用者帳號
                .password(passwordEncoder.encode(usersPO.getSecret())) //使用者密碼
                .authorities("ADMIN") // 使用者權限 這邊應該要去資料查出來 塞進去
                .build();
    }
}
