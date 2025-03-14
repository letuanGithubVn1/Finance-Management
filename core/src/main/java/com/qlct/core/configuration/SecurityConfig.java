//package com.qlct.core.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.ProviderManager;
//
//@Configuration
//public class SecurityConfig {
//
//    private final AuthenticationProvider googleAuthenticationProvider;
////    private final AuthenticationProvider facebookAuthenticationProvider;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    public SecurityConfig(AuthenticationProvider googleAuthenticationProvider,
//                         JwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.googleAuthenticationProvider = googleAuthenticationProvider;
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager() {
//        return new ProviderManager(googleAuthenticationProvider);
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable())  // Disable CSRF for API
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless API
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**").permitAll() // Cho phép truy cập API đăng nhập
//                        .anyRequest().authenticated()) // Các API khác phải xác thực
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Thêm filter JWT
//                .build();
//    }
//}
