package com.api.growin.exceptions;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom authentication entry point for handling unauthorized access attempts.
 * <p>
 *      This class implements {@link AuthenticationEntryPoint} to provide a standardized 
 *      response when an unauthenticated user tries to access a secured resource.
 * </p>
 */
@Component
public class AuthenticationEntryPointException implements AuthenticationEntryPoint{

    /**
     * Handles unauthorized access by setting the response status and adding a custom header.
     *
     * @param request           The HTTP request that resulted in an authentication exception.
     * @param response          The HTTP response to be sent to the client.
     * @param authException     The exception that occurred due to unauthorized access.
     * @throws IOException      If an input or output error occurs while handling the request.
     * @throws ServletException If a servlet error occurs while processing the request.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.addHeader("access_denied_reason", "authentication_required");
        response.sendError(403, "Access Denied");
    }
}
