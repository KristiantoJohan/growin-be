package com.api.growin.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum CurrentStage {
    DEFINE,
    DESIGN,
    DEVELOPMENT,
    DEBUG,
    DEPLOY;

    /**
     * Converts a string representation of a current stage into a {@code current stage} enum.
     * <p>
     *      This method ensures case-insensitive current stage parsing and logs an error
     *      if an invalid current stage is provided.
     * </p>
     *
     * @param current stage The string representation of the current stage.
     * @return The corresponding {@code Curremt stage} enum value.
     * @throws IllegalArgumentException if the current stage string is invalid.
     */
    @JsonCreator
    public static CurrentStage fromString(String currentStage) {
        try {
            return CurrentStage.valueOf(currentStage.toUpperCase()); // Mengonversi ke uppercase sebelum validasi
        } catch (IllegalArgumentException e) {
            log.error("Invalid current stage: {}", currentStage);
            throw new IllegalArgumentException("Invalid current stage: " + currentStage + ". Allowed values: DEFINE, DESIGN, DEVELOP, DEBUG, DEPLOY");
        }
    }

    /**
     * Converts the enum value to a string for JSON serialization.
     *
     * @return The string representation of the current stage.
     */
    @JsonValue
    public String toValue() {
        return this.name();
    }
}
