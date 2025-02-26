package com.api.growin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for handling authentication requests.
 * <p>
 *      This class is used to encapsulate user login credentials, including 
 *      the username and password, when making authentication requests.
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
public class loginRequest {

    /** The username provided by the user for authentication. */
    private String username;
    
    /** The password provided by the user for authentication. */
    private String password;
}
