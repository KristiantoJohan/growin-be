package com.api.growin.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Progress {
    COMPLETE,
    INCOMPLETE;
	
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
    public static Progress fromString(String progress) {
        try {
            return Progress.valueOf(progress.toUpperCase()); // Mengonversi ke uppercase sebelum validasi
        } catch (IllegalArgumentException e) {
            log.error("Invalid progress: {}", progress);
            throw new IllegalArgumentException("Invalid progress: " + progress + ". Allowed values: COMPLETE, INCOMPLETE");
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