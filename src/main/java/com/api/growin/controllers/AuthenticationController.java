package com.api.growin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.growin.dto.request.loginRequest;
import com.api.growin.dto.request.LogoutRequest;
import com.api.growin.dto.request.RefreshRequest;
import com.api.growin.dto.request.RegisterRequest;
import com.api.growin.dto.response.loginResponse;
import com.api.growin.dto.response.LogoutResponse;
import com.api.growin.dto.response.RefreshResponse;
import com.api.growin.dto.response.RegisterResponse;
import com.api.growin.helpers.HttpResponse;
import com.api.growin.services.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller for authentication-related operations.
 * <p>
 *      Handles user registration, login, token refresh, and logout.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    /**
     * Registers a new user.
     *
     * @param registerRequest The registration details.
     * @return A response indicating the success of user registration.
     */
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = authenticationService.register(registerRequest);
        return HttpResponse.successResponse("Successfully registering a new user", response);
    }

    /**
     * Authenticates a user and generates an access token.
     *
     * @param authenticationRequest The login credentials.
     * @return A response containing the authentication token.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody loginRequest authenticationRequest) {        
        loginResponse response = authenticationService.login(authenticationRequest);
        return HttpResponse.successResponse("Successfully login", response);        
    }

    /**
     * Refreshes an authentication token using a refresh token.
     *
     * @param refreshToken The refresh token.
     * @return A response containing the new authentication token.
     */
    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody RefreshRequest refreshRequest) {
        RefreshResponse response = authenticationService.refreshToken(refreshRequest);
        return HttpResponse.successResponse("New token generated", response);        
    }

    /**
     * Endpoint to handle user when logging out the system
     *
     * @param refreshToken The refresh token.
     * @return A response indicates the logging out process successfully done
     */
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody LogoutRequest logoutRequest) {
        LogoutResponse response = authenticationService.logout(logoutRequest.getToken());
        return HttpResponse.successResponse(response.toString());    
    }
}