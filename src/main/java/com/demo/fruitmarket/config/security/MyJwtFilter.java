package com.demo.fruitmarket.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author YeeDer
 * @since 2023/4/7 PM 12:04
 **/
public class MyJwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("Start MyJwtFilter doFilterInternal");

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        System.out.println("authorizationHeader = " + authorizationHeader);

        if ("ABC".equals(authorizationHeader)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("username", null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);
    }
}
