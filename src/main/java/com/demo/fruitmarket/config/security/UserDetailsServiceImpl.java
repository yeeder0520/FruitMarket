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

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("來惹");

        Optional<UsersPO> byUsername = usersRepo.findByUsername(userId);
        UsersPO usersPO1 = byUsername.get();
        System.out.println("usersPO1 = " + usersPO1);

        UsersPO usersPO = usersRepo.findByUsername(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

        System.out.println("結束惹");

        return User.builder()
                .username(usersPO.getUsername())
                .password(passwordEncoder.encode(usersPO.getSecret()))
                .authorities("ADMIN")
                .build();
    }
}