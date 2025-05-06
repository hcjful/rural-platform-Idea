package com.rural.platform.utils;

import com.rural.platform.entity.User;
import com.rural.platform.entity.AuthUser;
import com.rural.platform.entity.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUtils {
    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);

    public static Long getUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                log.warn("No authentication found in SecurityContext");
                return null;
            }

            log.info("Authentication details: isAuthenticated={}, authorities={}", 
                authentication.isAuthenticated(), 
                authentication.getAuthorities());
            
            Object principal = authentication.getPrincipal();
            if (principal == null) {
                log.warn("Principal is null");
                return null;
            }

            log.info("Principal class: {}", principal.getClass().getName());
            
            if (principal instanceof AuthUser) {
                AuthUser authUser = (AuthUser) principal;
                log.info("Found AuthUser with id: {}", authUser.getId());
                return authUser.getId();
            } else if (principal instanceof User) {
                User user = (User) principal;
                log.info("Found User with id: {}", user.getId());
                return user.getId();
            } else if (principal instanceof LoginUser) {
                log.warn("LoginUser found in SecurityContext, but no ID available");
                return null;
            } else if (principal instanceof String) {
                log.warn("String username found in SecurityContext: {}", principal);
                return null;
            }
            
            log.warn("Unexpected principal type: {}", principal.getClass().getName());
            return null;
        } catch (Exception e) {
            log.error("Error getting user ID from SecurityContext", e);
            return null;
        }
    }
}
