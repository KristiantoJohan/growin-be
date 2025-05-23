package com.api.growin.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum PackageLevel {
	 BASIC,
	 INTERMEDIATE,
	 PRO;

	 /**
		* Converts a string representation of a demand into a {@code level} enum.
		* <p>
		*      This method ensures case-insensitive demand parsing and logs an error
		*      if an invalid demand is provided.
		* </p>
		*
		* @param level The string representation of the level.
		* @return The corresponding {@code level} enum value.
		* @throws IllegalArgumentException if the level string is invalid.
		*/
	 @JsonCreator
	 public static PackageLevel fromString(String level) {
			try {
				 return PackageLevel.valueOf(level.toUpperCase()); // Mengonversi ke uppercase sebelum validasi
			} catch (IllegalArgumentException e) {
				 log.error("Invalid current stage: {}", level);
				 throw new IllegalArgumentException("Invalid package level: " + level + ". Allowed values: BASIC, INTERMEDIATE, PRO");
			}
	 }

	 /**
		* Converts the enum value to a string for JSON serialization.
		*
		* @return The string representation of the level.
		*/
	 @JsonValue
	 public String toValue() {
			return this.name();
	 }
}
