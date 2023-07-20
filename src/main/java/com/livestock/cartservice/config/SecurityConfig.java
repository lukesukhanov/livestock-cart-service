package com.livestock.cartservice.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain productsInCartFilterChain(HttpSecurity http) throws Exception {
    return http
        .securityMatcher("/productsInCart/**")
        .securityContext(securityContext -> securityContext
            .requireExplicitSave(true))
        .headers(headers -> headers
            .httpStrictTransportSecurity(hsts -> hsts
                .disable()))
        .cors(cors -> cors
            .disable())
        .csrf(csrf -> csrf
            .disable())
        .logout(logout -> logout
            .disable())
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(withDefaults())
            .authenticationEntryPoint(defaultAuthenticationEntryPoint()))
        .requestCache(requestCache -> requestCache
            .disable())
        .anonymous(anonymous -> anonymous
            .disable())
        .sessionManagement(sessionManagement -> sessionManagement
            .disable())
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers(HttpMethod.GET, "/productsInCart").hasAuthority("SCOPE_cart")
            .requestMatchers(HttpMethod.POST, "/productsInCart").hasAuthority("SCOPE_cart.write")
            .requestMatchers(HttpMethod.DELETE, "/productsInCart/**").hasAuthority("SCOPE_cart.write"))
        .build();
  }

  @Bean
  AuthenticationEntryPoint defaultAuthenticationEntryPoint() {
    return (request, response, e) -> {
      response.sendError(
          HttpStatus.UNAUTHORIZED.value(),
          HttpStatus.UNAUTHORIZED.getReasonPhrase());
    };
  }
}
