package com.smallhowe.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallhowe.entity.RestBean;
import com.smallhowe.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Resource
    private AccountService accountService;
    @Resource
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> {
                    // 针对HTTP请求进行授权规则设置
                    request.requestMatchers("/api/auth/**","/img/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .formLogin(form -> {
                    // 配置登录页
                    form
                            .loginProcessingUrl("/api/auth/login")
                            .successHandler(this::onAuthenticationSuccess)
                            .failureHandler(this::onAuthenticationFailure);
                })
                .logout(l -> {
                    l.logoutUrl("/api/auth/logout")
                            .logoutSuccessHandler(this::onLogoutSuccess)
                            .invalidateHttpSession(true)
                            .clearAuthentication(true);
                })
                .rememberMe(r -> {
                    r
                            .rememberMeParameter("remember")
                            .tokenRepository(this.tokenRepository())
                            .tokenValiditySeconds(3600 * 24 * 7);
                })
                .userDetailsService(accountService)
                .csrf(c -> {
                    c.disable();
                })
                .cors(cors -> {
                    cors.configurationSource(this::corsConfigurationSource);
                })
                .exceptionHandling(e -> {
                    e.authenticationEntryPoint(this::commence);
                });

        return http.build();
    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    private CorsConfiguration corsConfigurationSource(HttpServletRequest httpServletRequest) {
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedOriginPattern("*");
//        允许携带Cookie
        cors.setAllowCredentials(true);
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        cors.addExposedHeader("*");
        return cors;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception {
        return security
                .userDetailsService(accountService)
                .getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        ObjectMapper om = new ObjectMapper();
        String result = om.writeValueAsString(RestBean.success("登录成功"));
        response.getWriter().write(result);

    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        ObjectMapper om = new ObjectMapper();
        String result = om.writeValueAsString(RestBean.failure(401, exception.getMessage()));
        response.getWriter().write(result);

    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        ObjectMapper om = new ObjectMapper();
        String result = om.writeValueAsString(RestBean.success("退出成功"));
        response.getWriter().write(result);

    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        ObjectMapper om = new ObjectMapper();
        String result = om.writeValueAsString(RestBean.failure(401, "未登录"));
        response.getWriter().write(result);
    }

}
