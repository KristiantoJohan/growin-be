package com.api.growin.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Demand {
	LOW,
	LOW_MEDIUM,
	MEDIUM,
	HIGH_MEDIUM,
	HIGH;
	
	/**
     * Converts a string representation of a demand into a {@code current stage} enum.
     * <p>
     *      This method ensures case-insensitive demand parsing and logs an error
     *      if an invalid demand is provided.
     * </p>
     *
     * @param current stage The string representation of the demand.
     * @return The corresponding {@code demand} enum value.
     * @throws IllegalArgumentException if the demand string is invalid.
     */
    @JsonCreator
    public static Demand fromString(String demand) {
        try {
            return Demand.valueOf(demand.toUpperCase()); // Mengonversi ke uppercase sebelum validasi
        } catch (IllegalArgumentException e) {
            log.error("Invalid current stage: {}", demand);
            throw new IllegalArgumentException("Invalid demand: " + demand + ". Allowed values: LOW, LOW_MEDIUM, MEDIUM, HIGH_MEDIUM, HIGH");
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
