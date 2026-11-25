package org.example.springboot.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // 1. 如果是登录、注册等接口，直接放行
        if (path.equals("/login") || path.equals("/register") || path.equals("/api/send-code") || request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        // 2. 校验 Token
        if (token != null && jwtUtil.verifyToken(token)) {
            Long userId = jwtUtil.getUserId(token);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null, null);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 【核心修改】千万不要写 else 拦截！无论有没有 Token，都交由 filterChain 往下走
        // 后面如果接口需要权限，Spring Security 会自己拦截并返回 401，不需要在过滤器写死
        filterChain.doFilter(request, response);
    }
}

