package com.api.growin.exceptions;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom handler for handling access denied exceptions in the application.
 * <p>
 *      This class implements {@link AccessDeniedHandler} to provide a standardized 
 *      response when a user attempts to access a resource without the necessary permissions.
 * </p>
 */
@Component
public class AccessDeniedHandlerException implements AccessDeniedHandler {

    /**
     * Handles access denied exceptions by setting the response status and adding a custom header.
     *
     * @param request               The HTTP request that resulted in an access denied exception.
     * @param response              The HTTP response to be sent to the client.
     * @param accessDeniedException The exception that occurred when access was denied.
     * @throws IOException          If an input or output error occurs while handling the request.
     * @throws ServletException     If a servlet error occurs while processing the request.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.addHeader("access_denied_reason", "not_authorized");
        response.sendError(403, "Access Denied");   
    }
}