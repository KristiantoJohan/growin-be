package com.api.growin.controllers.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.growin.dto.request.auth.LoginRequest;
import com.api.growin.dto.request.auth.LogoutRequest;
import com.api.growin.dto.request.auth.RefreshRequest;
import com.api.growin.dto.request.auth.RegisterRequest;
import com.api.growin.dto.response.auth.LoginResponse;
import com.api.growin.dto.response.auth.LogoutResponse;
import com.api.growin.dto.response.auth.RefreshResponse;
import com.api.growin.dto.response.auth.RegisterResponse;
import com.api.growin.services.auth.AuthenticationService;
import com.api.growin.utils.HttpResponse;

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

        /* Sending the response */
        return HttpResponse.successResponse("Successfully registering a new user", response);
    }

    /**
     * Authenticates a user and generates an access token.
     *
     * @param authenticationRequest The login credentials.
     * @return A response containing the authentication token.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest authenticationRequest, HttpServletResponse httpServletResponse) {
        LoginResponse response = authenticationService.login(authenticationRequest, httpServletResponse);
        
        /* Sending the response */
        return HttpResponse.successResponse("Successfully login", response);        
    }

    /**
     * Refreshes an authentication token using a refresh token.
     *
     * @param refreshRequest The refresh token.
     * @return A response containing the new authentication token.
     */
//    @PostMapping("/refresh")
//    public ResponseEntity<Object> refresh(@RequestBody RefreshRequest refreshRequest, HttpServletResponse httpServletResponse) {
//        RefreshResponse response = authenticationService.refreshToken(refreshRequest, httpServletResponse);
//
//        /* Sending the response */
//        return HttpResponse.successResponse("New token generated", response);
//    }

    /**
     * Endpoint to handle user when logging out the system
     *
     * @return A response indicates the logging out process successfully done
     */
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest logoutRequest, HttpServletResponse httpServletResponse) {
        LogoutResponse response = authenticationService.logout(logoutRequest, httpServletResponse);

        /* Sending the response */
        return HttpResponse.successResponse(response.toString());
    }
}