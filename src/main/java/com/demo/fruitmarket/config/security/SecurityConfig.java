package com.demo.fruitmarket.config.security;

import com.demo.fruitmarket.repository.UsersRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UsersRepo usersRepo;

    public SecurityConfig(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                                .antMatchers("/users/*", "/fruitMarket/*").hasAuthority("ADMIN")
                                .antMatchers("/login", "/h2/*").permitAll()
//                        .anyRequest().authenticated()
                )
                .csrf().disable()
                /*驗證Token*/
                .addFilterBefore(new MyJwtFilter(), UsernamePasswordAuthenticationFilter.class)
                /*驗證帳號密碼，發Token*/
                .addFilterBefore(new MyAuthenticationFilter("/login", myAuthenticationManager()), UsernamePasswordAuthenticationFilter.class)
                /*讓 Spring Security 在驗證後不會在創建 Session*/
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /* 會跳出登入畫面 */
                .and()
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyUserDetailsService myUserDetailsService() {
        return new MyUserDetailsService(usersRepo);
    }

    @Bean
    public MyAuthenticationManager myAuthenticationManager() {
        return new MyAuthenticationManager(myUserDetailsService(), passwordEncoder());
    }


}
