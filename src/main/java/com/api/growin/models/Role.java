package com.api.growin.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.extern.slf4j.Slf4j;

/**
 * Enumeration representing the different roles in the system.
 * <p>
 * This enum defines the available user roles and provides 
 * JSON serialization and deserialization mechanisms.
 * </p>
 */
@Slf4j
public enum Role {
    USER,
    ADMIN;

    /**
     * Converts a string representation of a role into a {@code Role} enum.
     * <p>
     *      This method ensures case-insensitive role parsing and logs an error
     *      if an invalid role is provided.
     * </p>
     *
     * @param role The string representation of the role.
     * @return The corresponding {@code Role} enum value.
     * @throws IllegalArgumentException if the role string is invalid.
     */
    @JsonCreator
    public static Role fromString(String role) {
        try {
            return Role.valueOf(role.toUpperCase()); // Mengonversi ke uppercase sebelum validasi
        } catch (IllegalArgumentException e) {
            log.error("Invalid role: {}", role);
            throw new IllegalArgumentException("Invalid role: " + role + ". Allowed values: USER, ADMIN");
        }
    }

    /**
     * Converts the enum value to a string for JSON serialization.
     *
     * @return The string representation of the role.
     */
    @JsonValue
    public String toValue() {
        return this.name();
    }
}
