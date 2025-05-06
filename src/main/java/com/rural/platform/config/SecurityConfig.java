package com.rural.platform.config;

import com.rural.platform.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
                // 认证相关
                .antMatchers("/login", "/register", "/logout", "/captcha/**").permitAll()
                .antMatchers("/auth/register", "/auth/check-usercode", "/auth/check-email", "/auth/check-phone").permitAll()
                
                // API 路径
                .antMatchers("/api/**").permitAll()
                .antMatchers("/cultural-activities/**").permitAll()
                .antMatchers("/product/img-upload", "/img/upload").permitAll()
                .antMatchers("/orders/**", "/products/**", "/cart/**").permitAll()
                .antMatchers("/volunteer-activities/**", "/weather/**").permitAll()
                .antMatchers("/notifications/**", "/talents/**", "/comments/**").permitAll()
                .antMatchers("/warning/**", "/user/**", "/multilevel/**").permitAll()
                
                // 静态资源
                .antMatchers("/", "/index.html", "/static/**", "/assets/**").permitAll()
                .antMatchers("/css/**", "/js/**", "/img/**", "/fonts/**").permitAll()
                
                // Swagger文档
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/webjars/**").permitAll()
                
                // 其他所有请求需要认证
                .anyRequest().authenticated()
                .and()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
} 