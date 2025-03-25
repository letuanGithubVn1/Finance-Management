package com.qlct.core.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.qlct.core.service.UserDetailsServiceImpl;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailService;

  public JwtAuthFilter(JwtTokenUtil jwtUtil, UserDetailsServiceImpl userDetailService) {
    this.jwtUtil = jwtUtil;
    this.userDetailService = userDetailService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException, java.io.IOException {
    String header = request.getHeader("Authorization");
        
    if (header == null || !header.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    String token = header.replace("Bearer ", "");
    if (!jwtUtil.validateToken(token)) {
      chain.doFilter(request, response);
      return;
    }

    String username = jwtUtil.getUsernameFromToken(token);
    UserDetails userDetails = userDetailService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUtil.getUsernameFromToken(token), null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    chain.doFilter(request, response);
  }
}


















