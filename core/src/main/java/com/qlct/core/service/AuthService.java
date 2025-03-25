package com.qlct.core.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.qlct.core.dto.LoginRequest;
import com.qlct.core.dto.LoginResponse;
import com.qlct.core.utils.JwtTokenUtil;


@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtUtil;
    
    public AuthService(AuthenticationManager authenticationManager, JwtTokenUtil jwtUtil) {
    	this.authenticationManager = authenticationManager;
    	this.jwtUtil = jwtUtil;
    }
    
    // login với email/password
	public LoginResponse authenticateUser(LoginRequest loginRequest) {
        // Xác thực qua email và password
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authenticatedUser = authenticationManager.authenticate(authentication);
        
        // Tạo JWT token cho người dùng đã xác thực
        String accessToken = jwtUtil.generateToken(authenticatedUser.getName());
        String refreshToken = jwtUtil.generateRefreshToken(authenticatedUser.getName());

        return new LoginResponse(accessToken, refreshToken);
    }
	
	// login với google
	 public LoginResponse loginWithGoogle(String authorizationCode) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, authorizationCode);
        Authentication authenticatedUser = authenticationManager.authenticate(authentication);

        String accessToken = jwtUtil.generateToken(authenticatedUser.getName());
        String refreshToken = jwtUtil.generateRefreshToken(authenticatedUser.getName());

        return new LoginResponse(accessToken, refreshToken);
    }
}

















