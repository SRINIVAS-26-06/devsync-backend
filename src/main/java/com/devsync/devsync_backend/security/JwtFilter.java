// package com.devsync.devsync_backend.security;

// import jakarta.servlet.*;
// import jakarta.servlet.http.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.*;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.*;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;

// @Component
// public class JwtFilter extends OncePerRequestFilter {

//     @Autowired
//     private JwtUtil jwtUtil;

//     @Autowired
//     private CustomUserDetailsService userDetailsService;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain chain) throws ServletException, IOException {
//         final String authHeader = request.getHeader("Authorization");
//         String email = null;
//         String jwt = null;

//         if (authHeader != null && authHeader.startsWith("Bearer ")) {
//             jwt = authHeader.substring(7);
//             email = jwtUtil.extractUsername(jwt);
//         }

//         if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//             UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//             if (jwtUtil.validateToken(jwt)) {
//                 UsernamePasswordAuthenticationToken token =
//                         new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                 token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

//                 SecurityContextHolder.getContext().setAuthentication(token);
//             }
//         }
//         chain.doFilter(request, response);
//     }
// }


package com.devsync.devsync_backend.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain) throws ServletException, IOException {

    // Log request details
    logger.info("Incoming request to: " + request.getRequestURI());
    
    // Skip filtering for auth endpoints and error endpoint
    String path = request.getServletPath();
    if (path.startsWith("/api/auth") || path.equals("/error")) {
        logger.info("Skipping JWT filter for path: " + path);
        filterChain.doFilter(request, response);
        return;
    }

    try {
        String authHeader = request.getHeader("Authorization");
        logger.info("Authorization Header: " + (authHeader != null ? "Present" : "Not present"));
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("No JWT token found in request headers");
            filterChain.doFilter(request, response);
            return;
        }
        
        String jwt = authHeader.substring(7);
        String email = jwtUtil.extractUsername(jwt);
        
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            
            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(token);
                logger.info("Authentication successful for user: " + email);
            } else {
                logger.warn("JWT token validation failed");
            }
        }
    } catch (Exception e) {
        logger.error("JWT Authentication failed: " + e.getMessage(), e);
    }
    
    filterChain.doFilter(request, response);
}
}
