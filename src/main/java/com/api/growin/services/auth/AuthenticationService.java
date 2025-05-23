package com.api.growin.services.auth;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import com.api.growin.utils.CookiesOperator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.growin.dto.request.auth.LoginRequest;
import com.api.growin.dto.request.auth.RefreshRequest;
import com.api.growin.dto.request.auth.RegisterRequest;
import com.api.growin.dto.response.auth.LoginResponse;
import com.api.growin.dto.response.auth.LogoutResponse;
import com.api.growin.dto.response.auth.RefreshResponse;
import com.api.growin.dto.response.auth.RegisterResponse;
import com.api.growin.exceptions.InvalidCredentialsException;
import com.api.growin.exceptions.RefreshTokenNotFoundException;
import com.api.growin.exceptions.TokenInvalidException;
import com.api.growin.exceptions.UsernameAlreadyExistsException;
import com.api.growin.models.RefreshToken;
import com.api.growin.models.User;
import com.api.growin.repositories.RefreshTokenRepository;
import com.api.growin.repositories.UserRepository;
import com.api.growin.utils.JwtUtilities;

import lombok.RequiredArgsConstructor;

/**
 * Service class for handling authentication-related operations, including user registration,
 * login, token refresh, and logout.
 * <p>
 *      This class provides transactional methods to ensure data consistency during authentication processes.
 *      It integrates with Spring Security, JWT for token-based authentication, and repositories for persistence.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    /* Initialize dependencies */
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtConfig;
    private final AuthenticationManager authenticationManager;
    private final CookiesOperator cookiesOperator;

    /**
     * Registers a new user in the system.
     *
     * @param registerRequest the request containing user details (username, password, role)
     * @return a {@link RegisterResponse} containing the registered user's details
     * @throws UsernameAlreadyExistsException if the username is already taken
     */
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {

        /* Check if the username already exist in database */
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists: " + registerRequest.getUsername());
        }

        /* Build the model of user before parsing it to repository */
        User user = User.builder()
            .username(registerRequest.getUsername())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .role(registerRequest.getRole())
            .build();
        
        /* Save the new user to database */
        user = userRepository.save(user);
        
        /* Build the response */
        return RegisterResponse.builder()
            .id(user.getId().toString())
            .username(user.getUsername())
            .role(user.getAuthorities().toString())
            .credentialsNonExpired(user.isCredentialsNonExpired())
            .accountNonExpired(user.isAccountNonExpired())
            .accountNonLocked(user.isAccountNonLocked())
            .enabled(user.isEnabled())
            .build();
    }

    /**
     * Authenticates a user and generates JWT and refresh tokens.
     *
     * @param authenticationRequest the request containing username and password
     * @return a {@link LoginResponse} containing access and refresh tokens
     * @throws InvalidCredentialsException if authentication fails due to incorrect credentials
     */
    public LoginResponse login(LoginRequest authenticationRequest, HttpServletResponse response) {
        /* Authenticate the username and password using authentication manager */
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        /**
         *  <p> Find the username from database, throw exception if not existed </p>
         *  <p> Generate access token and refresh token if the authentication process success </p>
         */
        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        String jwtToken = jwtConfig.generateToken(user);
        String refreshToken = jwtConfig.generateRefresh(new HashMap<>(), user);

        /* Build refreshToken model before parsing it to repository */
        RefreshToken storedToken = RefreshToken.builder()
            .user(user)
            .token(refreshToken)
            .expireAt(jwtConfig.extractExpireAt(refreshToken))
            .build();
        
        /* Build save the refresh token to database */
        storedToken = refreshTokenRepository.save(storedToken);

        /* Store tokens to http-only cookie */
        cookiesOperator.setCookie(response, "access_token", jwtToken, 7 * 24 * 60 * 60);
        cookiesOperator.setCookie(response, "refresh_token", refreshToken, 7 * 24 * 60 * 60);
        
        /* Build the response */
        return LoginResponse.builder()
            .userId(user.getId())
            .build();
    }

    /**
     * Handles the refresh token process by validating the provided refresh token, 
     * checking for its existence and expiration, and generating a new access token.
     *
     * <p> This method follows these steps:
     * <ul>
     *   <li> Extracts the user ID from the given refresh token. </li>
     *   <li> Retrieves the refresh token from the database associated with the extracted user ID. </li>
     *   <li> Validates whether the refresh token is still valid or has expired. </li>
     *   <li> Deletes the refresh token if it has expired and throws a {@link TokenInvalidException}. </li>
     *   <li> Generates a new access token if the refresh token is valid. </li>
     *   <li> Returns a {@link RefreshResponse} containing the new access token. </li>
     * </ul>
     *
     * @param refreshRequest The request object containing the refresh token that needs validation.
     * @return A {@link RefreshResponse} containing the newly generated access token.
     * @throws RefreshTokenNotFoundException If the provided refresh token is not found in the database.
     * @throws TokenInvalidException If the refresh token has expired.
     */
//    public RefreshResponse refreshToken(RefreshRequest refreshRequest, HttpServletResponse response) {
//        /* Extract userId from access token */
//        String userId = jwtConfig.extractId(refreshRequest.getToken());
//
//        RefreshToken getRefreshToken = refreshTokenRepository.findByUserId(UUID.fromString(userId)).orElseThrow(() -> new RefreshTokenNotFoundException("Invalid refresh token"));
//
//        /* Check if the token has expired */
//        if (getRefreshToken.getExpireAt().isBefore(LocalDateTime.now())) {
//            refreshTokenRepository.delete(getRefreshToken);
//            throw new TokenInvalidException("Invalid refresh token");
//        }
//
//        String newAccessToken = jwtConfig.generateToken(getRefreshToken.getUser());
//
//        /* Store tokens to http-only cookie */
//        cookiesOperator.setCookie(response, "access_token", newAccessToken, 7 * 24 * 60 * 60);
//
//        /* Send the message */
//        return RefreshResponse.builder()
//            .build();
//    }

    /**
     * Handles the logout process by invalidating the provided refresh token.
     *
     * <p>This method follows these steps:
     * <ul>
     *   <li> Checks whether the provided refresh token exists in the database.</li>
     *   <li> If the token is not found, throws a {@link RefreshTokenNotFoundException}.</li>
     *   <li> Deletes the refresh token from the database to ensure it can no longer be used.</li>
     *   <li> Returns a {@link LogoutResponse} indicating successful logout.</li>
     * </ul>
     *
     * @param refreshToken The refresh token to be invalidated upon logout.
     * @return A {@link LogoutResponse} containing a success message.
     * @throws RefreshTokenNotFoundException If the provided refresh token does not exist in the database.
     */
    public LogoutResponse logout(HttpServletRequest refreshToken, HttpServletResponse response) {
        /* Check if the userId existed in database */
        String getToken = cookiesOperator.getCookieValue(refreshToken, "refresh_token");
        RefreshToken tokenExist = refreshTokenRepository.findByToken(getToken).orElseThrow(() -> new RefreshTokenNotFoundException("Invalid refresh token"));
        
        /* delete the current refresh token from database */
        refreshTokenRepository.delete(tokenExist);

        /* Clear cookies */
        cookiesOperator.clearCookie(response, "access_token");
        cookiesOperator.clearCookie(response, "refresh_token");

        return LogoutResponse.builder()
            .message("Successfully logout")
            .build();
    }
}