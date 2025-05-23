package com.api.growin.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Grade {
	 LOW,
	 MIDDLE,
	 HIGH;

	 /**
		* Converts a string representation of a demand into a {@code Grade} enum.
		* <p>
		*      This method ensures case-insensitive demand parsing and logs an error
		*      if an invalid demand is provided.
		* </p>
		*
		* @param grade The string representation of the Grade.
		* @return The corresponding {@code Grade} enum value.
		* @throws IllegalArgumentException if the Grade string is invalid.
		*/
	 @JsonCreator
	 public static Grade fromString(String grade) {
			try {
				 return Grade.valueOf(grade.toUpperCase()); // Mengonversi ke uppercase sebelum validasi
			} catch (IllegalArgumentException e) {
				 log.error("Invalid current stage: {}", grade);
				 throw new IllegalArgumentException("Invalid grade: " + grade + ". Allowed values: LOW, MIDDLE, HIGH");
			}
	 }

	 /**
		* Converts the enum value to a string for JSON serialization.
		*
		* @return The string representation of the Grade.
		*/
	 @JsonValue
	 public String toValue() {
			return this.name();
	 }
}
