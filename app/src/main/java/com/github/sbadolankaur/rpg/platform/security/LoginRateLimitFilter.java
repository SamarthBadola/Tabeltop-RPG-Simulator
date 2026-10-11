package com.github.sbadolankaur.rpg.platform.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

public final class LoginRateLimitFilter extends OncePerRequestFilter {
    private final LoginAttemptLimiter loginAttemptLimiter;

    public LoginRateLimitFilter(LoginAttemptLimiter loginAttemptLimiter) {
        this.loginAttemptLimiter = loginAttemptLimiter;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        if ("POST".equals(request.getMethod())
                && "/login".equals(request.getServletPath())
                && loginAttemptLimiter.isBlocked(
                        request.getParameter("username"), request.getRemoteAddr())) {
            response.sendRedirect(request.getContextPath() + "/login?throttled");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
