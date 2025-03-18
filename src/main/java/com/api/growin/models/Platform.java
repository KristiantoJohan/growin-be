package com.api.growin.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Platform {
    IOS,
    ANDROID,
    WINDOWS,
    HYBRID;

    /**
     * Converts a string representation of a category into a {@code category} enum.
     * <p>
     *      This method ensures case-insensitive category parsing and logs an error
     *      if an invalid category is provided.
     * </p>
     *
     * @param category The string representation of the category.
     * @return The corresponding {@code category} enum value.
     * @throws IllegalArgumentException if the category string is invalid.
     */
    @JsonCreator
    public static Platform fromString(String platform) {
        try {
            return Platform.valueOf(platform.toUpperCase()); // Mengonversi ke uppercase sebelum validasi
        } catch (IllegalArgumentException e) {
            log.error("Invalid platform: {}", platform);
            throw new IllegalArgumentException("Invalid category: " + platform + ". Allowed values: IOS, ANDROID, WINDOWS, HYBRID");
        }
    }

    /**
     * Converts the enum value to a string for JSON serialization.
     *
     * @return The string representation of the category.
     */
    @JsonValue
    public String toValue() {
        return this.name();
    }
}
