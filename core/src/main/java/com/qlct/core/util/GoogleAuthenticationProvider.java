//package com.qlct.core.util;
//
//import java.util.Collections;
//import java.util.Optional;
//
//import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.stereotype.Component;
//
//import com.qlct.core.entity.User;
//import com.qlct.core.repository.UserRepository;
//
//@Component
//public class GoogleAuthenticationProvider implements AuthenticationProvider {
//
//    private final GoogleIdTokenVerifier googleVerifier;
//    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;
//
//    public GoogleAuthenticationProvider(GoogleIdTokenVerifier googleVerifier, 
//                                        UserRepository userRepository, 
//                                        JwtUtil jwtUtil) {
//        this.googleVerifier = googleVerifier;
//        this.userRepository = userRepository;
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) {
//        String idToken = authentication.getCredentials().toString();
//
//        try {
//            GoogleIdToken token = googleVerifier.verify(idToken);
//            if (token != null) {
//                GoogleIdToken.Payload payload = token.getPayload();
//                String email = payload.getEmail();
//
//                // Kiểm tra user đã tồn tại hay chưa
//                Optional<User> existingUser = userRepository.findByEmail(email);
//                User user = existingUser.orElseGet(() -> {
//                    User newUser = new User();
//                    newUser.setEmail(email);
////                    newUser.setProvider("GOOGLE");
//                    return userRepository.save(newUser);
//                });
//
//                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
//            }
//        } catch (Exception e) {
//            throw new BadCredentialsException("Invalid Google token");
//        }
//        throw new BadCredentialsException("Authentication failed");
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
