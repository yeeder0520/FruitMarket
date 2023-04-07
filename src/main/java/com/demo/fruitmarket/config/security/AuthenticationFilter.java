package com.demo.fruitmarket.config.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 初始化建構子
     *
     * @param defaultFilterProcessesUrl 登入的URL
     */
    public AuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    /**
     * 處理登入過程的參數與邏輯
     *
     * @param request  request
     * @param response response
     * @return Authentication
     * @throws AuthenticationException AuthenticationException
     * @throws IOException             IOException
     * @throws ServletException        ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        System.out.println("Start attemptAuthentication");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        UserDetails user = objectMapper.readValue(request.getInputStream(), MyUser.class);

        System.out.println(user.getUsername() + user.getPassword());

        String username = user.getUsername();
        username = HtmlUtils.htmlEscape(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                user.getPassword(),
                user.getAuthorities()
        );

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    /**
     * 處理授權成功的回應
     *
     * @param request    request
     * @param response   response
     * @param chain      chain
     * @param authResult authResult
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 身份驗證成功後，將 Authentication 物件存放在 SecurityContext 中
        System.out.println("successfulAuthentication");

        // 生成JWT token
        String token = "ABCDEFG";

        // 将JWT token添加到HTTP响应中
        response.addHeader("Authorization", "Bearer " + token);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("aaaaaaa");

    }

    /**
     * 處理授權失敗的回應
     *
     * @param request  request
     * @param response response
     * @param failed   failed
     * @throws IOException      IOException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        System.out.println("unsuccessfulAuthentication");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(failed.getMessage());
    }


}

