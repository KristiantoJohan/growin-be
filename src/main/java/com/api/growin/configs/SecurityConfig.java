package com.api.growin.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.growin.middlewares.JwtAuthenticationFilter;
import com.api.growin.models.Role;

import lombok.RequiredArgsConstructor;

/**
 * Security configuration for the application using Spring Security.
 * <p>
 *      This class is responsible for setting up the application's security policies,
 *      including authorization rules for endpoints, JWT-based authentication, 
 *      and session management configuration to be stateless.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /** Filter for processing JWT-based authentication before username-password authentication. */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /** Authentication provider used to verify user credentials. */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configures the security filter chain for the application.
     * <p>
     *      This configuration includes authorization rules, session management,
     *      and the application of the JWT authentication filter.
     * </p>
     *
     * @param http The {@link HttpSecurity} object used to define security configurations.
     * @return The configured {@link SecurityFilterChain} object.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request.requestMatchers(
                    "/api/v1/authentication/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/webjars/**"
                )
                .permitAll()
                .requestMatchers("/api/v1/admin/**").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/api/v1/user/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                .anyRequest().authenticated()
            )            
            .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}