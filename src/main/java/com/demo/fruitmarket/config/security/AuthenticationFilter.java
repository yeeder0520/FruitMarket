package com.demo.fruitmarket.config.security;

import com.demo.fruitmarket.entity.UsersPO;
import com.demo.fruitmarket.repository.UsersRepo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepo usersRepo;

    /**
     * 初始化建構子
     *
     * @param defaultFilterProcessesUrl 登入的URL
     * @param passwordEncoder           passwordEncoder
     * @param usersRepo                 usersRepo
     */
    public AuthenticationFilter(String defaultFilterProcessesUrl, PasswordEncoder passwordEncoder, UsersRepo usersRepo) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        this.passwordEncoder = passwordEncoder;
        this.usersRepo = usersRepo;
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

        MyUser user = objectMapper.readValue(request.getInputStream(), MyUser.class);
        String username = user.getUsername();

        UsersPO usersPO = usersRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + username));

        /*驗證密碼正確性
        參數1:使用者代帶入密碼
        參數2:DB密碼 (加密過) */
        boolean isPwdMatches = passwordEncoder.matches(user.getPassword(), usersPO.getSecret());
        if (!isPwdMatches) {
            throw new BadCredentialsException("Incorrect password");
        }

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                user.getAuthorities()
        );
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
        response.getWriter().write(authResult.toString());

    }

    /**
     * 處理授權失敗的回應
     *
     * @param request  request
     * @param response response
     * @param failed   failed
     * @throws IOException IOException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        System.out.println("unsuccessfulAuthentication");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(failed.getMessage());
    }


}

