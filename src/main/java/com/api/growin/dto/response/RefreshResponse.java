package com.api.growin.dto.response;

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
public class RefreshResponse {
    /**
     * The JWT (JSON Web Token) issued upon successful authentication.
     * <p>
     *      This token is used for authorizing API requests and typically has an expiration time.
     * </p>
     */
    private String accessToken;
}
