package com.demo.fruitmarket.config.security;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUser implements UserDetails {

    @JsonProperty("account")
    private String account;

    @JsonProperty("secret")
    private String secret;

    private Collection<? extends GrantedAuthority> authorities;

    public MyUser() {
    } // 添加一个无参构造函数

    public MyUser(String account, String secret, Collection<? extends GrantedAuthority> authorities) {
        this.account = account;
        this.secret = secret;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return secret;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
