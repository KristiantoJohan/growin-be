package com.api.growin.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user in the system.
 * <p>
 *      Implements {@link UserDetails} to integrate with Spring Security authentication.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Unique username for authentication.
     */
    @Column(unique = true)
    private String username;
    
    /**
     * Encrypted password for authentication.
     */
    private String password;

    /**
     * Timestamp indicating when the user was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update of user details.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Role assigned to the user, determining their permissions.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Collection of refresh tokens associated with the user for authentication.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RefreshToken> refreshTokens;
    
    /**
     *  Status whether the user is verified by the administrator or
     */
    @Column(name = "is_verified", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isVerified;

    /**
     * Returns the authorities granted to the user.
     * <p>
     *      This implementation returns a list with a single authority based on the user's role.
     * </p>
     *
     * @return a collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Retrieves the user's password.
     *
     * @return the user's password.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Retrieves the username (UUID) for authentication.
     *
     * @return the user's unique identifier as a string.
     */
    @Override
    public String getUsername() {
        return this.id.toString();
    }

    /**
     * Indicates whether the user's account is expired.
     *
     * @return {@code true} if the account is not expired, otherwise {@code false}.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     *
     * @return {@code true} if the account is not locked, otherwise {@code false}.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) are expired.
     *
     * @return {@code true} if credentials are not expired, otherwise {@code false}.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return {@code true} if the user is enabled, otherwise {@code false}.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
