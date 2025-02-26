package com.api.growin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for user registration responses.
 * <p>
 *      This class represents the response returned after a successful user registration process.
 *      It contains user details such as ID, username, role, and account status properties.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    /**
     * The unique identifier of the registered user.
     */
    private String id;

    /**
     * The username of the registered user.
     */
    private String username;

    /**
     * The role assigned to the registered user.
     * <p>
     *      This role determines the user's level of access and permissions in the system.
     * </p>
     */
    private String role;

    /**
     * Indicates whether the user's credentials (password) are still valid.
     * <p>
     *      If {@code false}, the user must reset their password before logging in.
     * </p>
     */
    private Boolean credentialsNonExpired;

    /**
     * Indicates whether the user's account is expired.
     * <p>
     * If {@code false}, the user account is no longer active due to expiration.
     * </p>
     */
    private Boolean accountNonExpired;

    /**
     * Indicates whether the user's account is locked.
     * <p>
     *      If {@code false}, the user cannot log in due to account restrictions.
     * </p>
     */
    private Boolean accountNonLocked;

    /**
     * Indicates whether the user account is enabled.
     * <p>
     *      If {@code false}, the user account is disabled and cannot be used for authentication.
     * </p>
     */
    private Boolean enabled;
}
