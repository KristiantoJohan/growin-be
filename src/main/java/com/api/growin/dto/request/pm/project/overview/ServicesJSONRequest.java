package com.api.growin.dto.request.pm.project.overview;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for submitting project vision and mission details.
 * <p>
 *     This class captures essential information about a project's purpose, 
 *     including its vision and mission statements.
 *     It also includes validation constraints to ensure data integrity.
 * </p>
 *
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-03-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicesJSONRequest {
    /** 
     * The vision statement of the project.
     * <p>
     *     This field must not be null or empty.
     * </p>
     */
    @NotNull(message = "Vision cannot be null")
    private String vision;

    /** 
     * The mission statement of the project.
     * <p>
     *     This field must not be null or empty.
     * </p>
     */
    @NotNull(message = "Mission cannot be null")
    private String mission;
}
