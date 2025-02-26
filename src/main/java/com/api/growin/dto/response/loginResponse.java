package com.api.growin.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for authentication responses.
 * <p>
 *      This class represents the response returned after a successful authentication process.
 *      It includes an authentication token (JWT) and a refresh token for session management.
 * </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class loginResponse {
    /**
     * The JWT (JSON Web Token) issued upon successful authentication.
     * <p>
     *      This token is used for authorizing API requests and typically has an expiration time.
     * </p>
     */
    private String accessToken;
    
    /**
     * The refresh token issued along with the authentication token.
     * <p>
     *      This token allows users to obtain a new authentication token without requiring 
     *      re-authentication using credentials.
     * </p>
     */
    private String refreshToken;

    /**
     * The unique identifier of the user.
     * <p>
     *     This ID is used to associate the user with various system operations and authentication processes.
     * </p>
     */
    private UUID userId;
}
