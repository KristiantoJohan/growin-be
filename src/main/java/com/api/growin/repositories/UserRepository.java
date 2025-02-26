package com.api.growin.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.Role;
import com.api.growin.models.User;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 *      This interface extends {@link JpaRepository}, providing built-in CRUD operations
 *      and additional query methods for retrieving user data based on different criteria.
 * </p>
 *
 * <p>
 *      The {@code @Repository} annotation marks this interface as a Spring Data repository,
 *      while {@code @EnableJpaRepositories} enables JPA repository support.
 * </p>
 */
@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Checks whether a user exists with the given username.
     *
     * @param username the username to check for existence.
     * @return {@code true} if a user with the given username exists, otherwise {@code false}.
     */
    Boolean existsByUsername(String username);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user.
     * @return an {@link Optional} containing the user if found, or empty if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves an optional user based on their assigned role.
     *
     * @param role the {@link Role} of the user.
     * @return an {@link Optional} containing the user if found, or empty if no user has the given role.
     */
    Optional<User> findByRole(Role role);
}