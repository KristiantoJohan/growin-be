package com.api.growin.helpers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * A utility class for generating standardized HTTP responses.
 * <p>
 *      This class provides static methods to generate structured JSON responses
 *      for both successful and error responses, ensuring consistency across the API.
 * </p>
 */
public class HttpResponse {

    /**
     * Generates a successful HTTP response with a message and data.
     *
     * @param message The success message.
     * @param data    The response data.
     * @return A {@link ResponseEntity} containing the structured response.
     */
    public static ResponseEntity<Object> successResponse(String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(
            "meta", Map.of(
                "success", false,
                "message", message
            )            
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Generates a successful HTTP response with a message and data.
     *
     * @param message The success message.
     * @param data    The response data.
     * @return A {@link ResponseEntity} containing the structured response.
     */
    public static ResponseEntity<Object> successResponse(String message, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(
            "meta", Map.of(
                "success", false,
                "message", message
            )            
        );

        response.put("data", data);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Generates a paginated success response.
     *
     * @param message The success message.
     * @param data    The response data.
     * @param page    The current page number.
     * @param limit   The number of items per page.
     * @param total   The total number of items.
     * @return A {@link ResponseEntity} containing the structured paginated response.
     */
    public static ResponseEntity<Object> successResponse(String message, Object data, int page, int limit, long total) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(
            "meta", Map.of(
                "success", true,
                "message", message,
                "page", page,
                "limit", limit,
                "total", total
            )
        );

        response.put("data", data);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Generates an error HTTP response with a message and status code.
     *
     * @param message The error message.
     * @param status  The HTTP status code.
     * @return A {@link ResponseEntity} containing the structured error response.
     */
    public static ResponseEntity<Object> errorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new LinkedHashMap<>();        
        response.put(
            "meta", Map.of(
                "success", false,
                "message", message
            )            
        );
                
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Generates an error HTTP response with a message, status code, and additional details.
     *
     * @param message The error message.
     * @param status  The HTTP status code.
     * @param details Additional error details (e.g., validation errors).
     * @return A {@link ResponseEntity} containing the structured error response.
     */
    public static ResponseEntity<Object> errorResponse(String message, HttpStatus status, Object details) {
        Map<String, Object> response = new LinkedHashMap<>();        
        response.put(
            "meta", Map.of(
                "success", false,
                "message", message
            )            
        );
        
        response.put("details", details);

        return ResponseEntity.status(status).body(response);
    }
}