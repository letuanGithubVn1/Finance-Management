package com.qlct.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.qlct.core.utils.JwtAuthFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;
  private final EmailPasswordAuthenticationProvider emailPasswordAuthenticationProvider;
  private final GoogleAuthenticationProvider googleAuthenticationProvider;

  public SecurityConfig(JwtAuthFilter jwtAuthFilter, EmailPasswordAuthenticationProvider emailPasswordAuthenticationProvider,
		  		     GoogleAuthenticationProvider googleAuthenticationProvider) {
      this.jwtAuthFilter = jwtAuthFilter;
      this.emailPasswordAuthenticationProvider = emailPasswordAuthenticationProvider;
      this.googleAuthenticationProvider = googleAuthenticationProvider;
  } 
  
  @Bean
  AuthenticationManager authenticationManager() {
      return new ProviderManager(emailPasswordAuthenticationProvider, googleAuthenticationProvider);
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())  // Disable CSRF for API
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
            .anyRequest().authenticated()) 
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
        .build();
  }
}
