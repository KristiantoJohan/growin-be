package com.api.growin.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.growin.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Application Security Configuration.
 * <p>
 * This class is responsible for configuring authentication and password encoding mechanisms
 * in the application. It provides beans for user authentication, password encoding, 
 * and authentication provider setup.
 * </p>
 *
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
    
    /**
     * Defines a {@link UserDetailsService} bean that fetches user details from the database.
     * <p>
     *      This method retrieves user information based on the provided username.
     *      If the user is not found, a {@link UsernameNotFoundException} is thrown.
     * </p>
     *
     * @return a {@link UserDetailsService} implementation that fetches user details from {@link UserRepository}.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Defines the authentication provider bean.
     * <p>
     *      The {@link DaoAuthenticationProvider} is used to authenticate users 
     *      based on their username and password. It uses {@link #userDetailsService()} 
     *      for retrieving user details and {@link #passwordEncoder()} for password hashing.
     * </p>
     *
     * @return an {@link AuthenticationProvider} configured with a user details service and password encoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Defines the authentication manager bean.
     * <p>
     *      The {@link AuthenticationManager} is retrieved from the {@link AuthenticationConfiguration}.
     *      This bean is required for handling authentication processes within the application.
     * </p>
     *
     * @param config the authentication configuration that provides the authentication manager.
     * @return an instance of {@link AuthenticationManager}.
     * @throws Exception if authentication manager retrieval fails.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Defines the password encoder bean.
     * <p>
     *      This method provides a {@link BCryptPasswordEncoder}, which is a secure hashing function
     *      used for encoding and verifying passwords.
     * </p>
     *
     * @return a {@link PasswordEncoder} implementation using BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

