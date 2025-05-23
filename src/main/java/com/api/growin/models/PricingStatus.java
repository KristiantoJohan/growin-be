package com.api.growin.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum PricingStatus {
	COMPLETE,
	REQUESTED,
	INCOMPLETE;
	
	/**
     * Converts a string representation of a pricing status into a {@code pricingStatus} enum.
     * <p>
     *      This method ensures case-insensitive pricing status parsing and logs an error
     *      if an invalid pricing status is provided.
     * </p>
     *
     * @param category The string representation of the pricing status.
     * @return The corresponding {@code pricingStatus} enum value.
     * @throws IllegalArgumentException if the pricing status string is invalid.
     */
    @JsonCreator
    public static PricingStatus fromString(String status) {
        try {
            return PricingStatus.valueOf(status.toUpperCase()); // Mengonversi ke uppercase sebelum validasi
        } catch (IllegalArgumentException e) {
            log.error("Invalid pricing status: {}", status);
            throw new IllegalArgumentException("Invalid category: " + status + ". Allowed values: COMPLETE, INCOMPLETE");
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
