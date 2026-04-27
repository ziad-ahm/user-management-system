package com.usermanagement.dashboard.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 1️⃣ لو مفيش token → كمّل عادي
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // 2️⃣ استخرج التوكن
        String token = header.substring(7);

        // 3️⃣ هات اليوزر من التوكن
        String username = jwtUtil.extractUsername(token);

        // 4️⃣ لو فيه يوزر ومفيش login قبل كده
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            var userDetails = userDetailsService.loadUserByUsername(username);

            // 5️⃣ تحقق من التوكن
            if (jwtUtil.isTokenValid(token, userDetails.getUsername())) {

                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // 6️⃣ سجّل اليوزر
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // 7️⃣ كمّل باقي السيستم
        chain.doFilter(request, response);
    }
}