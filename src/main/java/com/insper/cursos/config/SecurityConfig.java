package com.insper.cursos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .cors().and()
          .authorizeHttpRequests(auth -> auth
              .requestMatchers(HttpMethod.POST,   "/api/cursos/**").hasRole("ADMIN")
              .requestMatchers(HttpMethod.PUT,    "/api/cursos/**").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/cursos/**").hasRole("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/api/avaliacoes/**").hasRole("ADMIN")
              .requestMatchers("/api/**").authenticated()
          )
          .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}