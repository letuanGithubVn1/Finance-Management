package com.qlct.core.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.qlct.core.dto.LoginRequest;
import com.qlct.core.dto.LoginResponse;
import com.qlct.core.service.AuthService;

@RestController
@RequestMapping(value={"/api/auth"})
public class Authcontroller {
	private final AuthService authService;

    public Authcontroller(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
    	System.out.println(request.getEmail());
    	return authService.authenticateUser(request);
    }
    
    // Đăng nhập bằng Google (ID Token)
    @PostMapping("/google-login")
    public ResponseEntity<LoginResponse> loginWithGoogle(@RequestParam String code) {
    	LoginResponse response = authService.loginWithGoogle(code);
        return ResponseEntity.ok(response);
    }
}









