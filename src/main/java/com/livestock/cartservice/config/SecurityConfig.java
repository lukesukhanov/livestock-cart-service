package com.livestock.cartservice.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain productsInCartFilterChain(HttpSecurity http) throws Exception {
    return http
        .securityMatcher("/productsInCart", "/productsInCart/*")
        .securityContext(securityContext -> securityContext
            .requireExplicitSave(true))
        .headers(headers -> headers
            .httpStrictTransportSecurity(hsts -> hsts
                .disable()))
        .cors(cors -> cors
            .configurationSource(productsInCartCorsConfigurationSource()))
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
            .requestMatchers(HttpMethod.DELETE, "/productsInCart").hasAuthority("SCOPE_cart.write")
            .requestMatchers(HttpMethod.DELETE, "/productsInCart/*").hasAuthority("SCOPE_cart.write"))
        .build();
  }

  @Bean
  CorsConfigurationSource productsInCartCorsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500"));
    config.setAllowedMethods(List.of(
        HttpMethod.GET.toString(),
        HttpMethod.POST.toString(),
        HttpMethod.DELETE.toString()));
    config.setAllowedHeaders(List.of(
        HttpHeaders.AUTHORIZATION,
        HttpHeaders.CONTENT_TYPE,
        HttpHeaders.ACCEPT));
    config.setAllowCredentials(true);
    config.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/productsInCart", config);
    source.registerCorsConfiguration("/productsInCart/*", config);
    return source;
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