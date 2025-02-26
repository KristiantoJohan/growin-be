package com.api.growin.services;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.growin.repositories.UserRepository;

/**
 * Implementation of {@link UserDetailsService} that retrieves user details
 * from the database based on the provided username.
 * <p>
 *      This service is used by Spring Security to authenticate users.
 * </p>
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Constructs a new {@code UserDetailsServiceImpl} with the specified {@code UserRepository}.
     *
     * @param userRepository the repository used to access user data.
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their username (UUID) for authentication.
     *
     * @param username the username (UUID) of the user.
     * @return the {@link UserDetails} of the authenticated user.
     * @throws UsernameNotFoundException if no user is found with the given UUID.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Convert username into UUID
        UUID uuid = UUID.fromString(username);

        // Get user based on UUID
        return userRepository.findById(uuid).orElseThrow(() -> new RuntimeException("User not found with UUID: " + username));
    }
}
