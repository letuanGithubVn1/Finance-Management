package com.qlct.core.configuration;

import com.qlct.core.utils.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Component
public class GoogleAuthenticationProvider implements AuthenticationProvider {
	
	@Value("${google_client_id}")
	private String clientId;

	@Value("${google_client_secret}")
	private String clientSecret;

	@Value("${google_redirect_uri}")
    private String redirectUri;

    private final RestTemplate restTemplate;
    private final JwtTokenUtil jwtUtil;

    public GoogleAuthenticationProvider(RestTemplate restTemplate, JwtTokenUtil jwtUtil) {
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
    }
   
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authorizationCode = (String) authentication.getCredentials();

        // Exchange authorization code for Google tokens
        ResponseEntity<Map<String, Object>> response = exchangeAuthCodeForTokens(authorizationCode);
        
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to retrieve access token from Google");
        }

        Map<String, Object> tokenData = response.getBody();
        String googleAccessToken = (String) tokenData.get("access_token");

        // Fetch user info from Google
        Map<String, Object> userInfo = fetchGoogleUserInfo(googleAccessToken);
        if (userInfo == null) {
            throw new RuntimeException("Failed to retrieve user info from Google");
        }

        String email = (String) userInfo.get("email");

        // Generate our own JWT token
        String ourAccessToken = jwtUtil.generateToken(email);

        User user = new User(email, "", Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(user, ourAccessToken, user.getAuthorities());
    }

    
    private ResponseEntity<Map<String, Object>> exchangeAuthCodeForTokens(String authorizationCode) {
        String tokenUrl = "https://oauth2.googleapis.com/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
	    requestBody.add("code", authorizationCode);
	    requestBody.add("client_id", clientId);
	    requestBody.add("client_secret", clientSecret);
	    requestBody.add("redirect_uri", redirectUri);
	    requestBody.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        // Đảm bảo kiểu trả về là Map<String, Object>
        return restTemplate.exchange(tokenUrl, HttpMethod.POST, request, new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    private Map<String, Object> fetchGoogleUserInfo(String accessToken) {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> request = new HttpEntity<>(headers);

        // Cập nhật kiểu trả về là Map<String, Object>
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, new ParameterizedTypeReference<Map<String, Object>>() {});
                
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
    }

    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}


































