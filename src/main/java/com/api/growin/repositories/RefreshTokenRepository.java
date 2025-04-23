package com.api.growin.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.RefreshToken;
import com.api.growin.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link RefreshToken} entities.
 * <p>
 *      This interface extends {@link JpaRepository}, providing built-in CRUD operations
 *      and additional query methods for retrieving refresh tokens based on different criteria.
 * </p>
 *
 * <p>
 *      The refresh token mechanism is used for implementing secure authentication,
 *      allowing users to request new access tokens without re-entering credentials.
 * </p>
 *
 * <p>
 *      Annotated with {@code @Repository} to indicate that this interface is a Spring Data repository.
 *      The {@code @EnableJpaRepositories} annotation enables JPA repository support.
 * </p>
 */
@Repository
@EnableJpaRepositories
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    /**
     * Retrieves all refresh tokens associated with a given user.
     *
     * @param user the user whose refresh tokens are to be retrieved.
     * @return a list of {@link RefreshToken} entities linked to the specified user.
     */
    List<RefreshToken> findByUser(User user);
    
    /**
     * Retrieves an optional refresh token based on the user ID.
     *
     * @param userId the UUID of the user.
     * @return an {@link Optional} containing the refresh token if found, or empty if not found.
     */
    Optional<RefreshToken> findByUserId(UUID userId);

    /**
     * Retrieves an optional refresh token based on its token value.
     *
     * @param token the refresh token string.
     * @return an {@link Optional} containing the refresh token if found, or empty if not found.
     */
    Optional<RefreshToken> findByToken(String token);
}