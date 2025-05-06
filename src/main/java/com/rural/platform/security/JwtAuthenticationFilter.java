package com.rural.platform.security;

import com.rural.platform.entity.User;
import com.rural.platform.service.UserService;
import com.rural.platform.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    
    private final List<String> publicPaths = Arrays.asList(
        // 认证相关
        "/login",
        "/register",
        "/logout",
        "/captcha/**",
        "/auth/register",
        "/auth/check-usercode",
        "/auth/check-email",
        "/auth/check-phone",
        
        // API 路径
        "/api/**",
        "/cultural-activities/**",
        "/product/img-upload",
        "/img/upload",
        "/orders/**",
        "/products/**",
        "/cart/**",
        "/volunteer-activities/**",
        "/weather/**",
        "/notifications/**",
        "/talents/**",
        "/comments/**",
        "/warning/**",
        "/user/**",
        "/multilevel/**",
        
        // 静态资源
        "/",
        "/index.html",
        "/static/**",
        "/assets/**",
        "/css/**",
        "/js/**",
        "/img/**",
        "/fonts/**",
        
        // Swagger文档
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/v2/api-docs",
        "/webjars/**"
    );

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        // 检查是否是公开路径
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = extractToken(request);
            if (token != null && jwtUtils.validateToken(token)) {
                String userCode = jwtUtils.getUserCodeFromToken(token);
                User user = userService.getUserByCode(userCode);
                
                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("User authenticated: {}", user.getUserCode());
                }
            }
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return publicPaths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
} 