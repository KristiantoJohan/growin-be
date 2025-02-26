package com.api.growin.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a refresh token used for authentication.
 * <p>
 *      A refresh token is used to generate new access tokens without requiring
 *      the user to log in again. This entity stores the refresh token along with
 *      its expiration time and the associated user.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_refresh_token")
public class RefreshToken {

    /**
     * Unique identifier for the refresh token.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The actual refresh token string.
     * This must be unique and cannot be null.
     */
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    /**
     * Expiration time of the refresh token.
     * Once expired, the token can no longer be used to generate new access tokens.
     */
    @Column(name = "expire_at", nullable = false)
    private LocalDateTime expireAt;

    /**
     * The user associated with this refresh token.
     * This establishes a many-to-one relationship between refresh tokens and users.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Timestamp indicating when the refresh token was created.
     * This value is automatically generated and cannot be updated.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the refresh token was last updated.
     * This value is automatically updated when the entity is modified.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
