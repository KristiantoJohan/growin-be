package com.api.growin.dto.request;

import com.api.growin.models.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for user registration requests.
 * <p>
 *      This class is used to capture user input when registering a new account,
 *      including username, password, and role. It also includes validation 
 *      constraints to ensure data integrity.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    /** 
     * The username provided by the user during registration.
     * <p>
     *      This field must not be blank.
     * </p>
     */
    @NotBlank(message = "Username cannot be blank")
    private String username;

    /** 
     * The password provided by the user during registration.
     * <p>
     *      The password must:
     *      <ul>
     *          <li> Be at least 8 characters long </li>
     *          <li> Contain at least one uppercase letter </li>
     *          <li> Contain at least one lowercase letter </li>
     *          <li> Contain at least one digit </li>
     *          <li> Contain at least one special character (e.g., @, $, !, %) </li>
     *      </ul>
     * </p>
     */
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must have 8 characters minimum")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
        message = "Password must be a combination of alphabetic, numeric, and symbols"
    )
    private String password;

    /** 
     * The role assigned to the user upon registration.
     * <p>
     *      This field must not be null.
     * </p>
     */
    @NotNull(message = "Role cannot be null")
    private Role role;
}