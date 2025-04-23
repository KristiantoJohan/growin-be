package com.api.growin.dto.response.projects;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Data Transfer Object (DTO) for presenting a concise summary of a project.
 * <p>
 *     This class is typically used in list views or dashboards to provide
 *     essential information about a project, such as its identity, progress,
 *     category, platform, and timestamps.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectBriefResponse {

    /**
     * The unique identifier of the initialized project.
     */
    private String id;

    /**
     * The ID of the last user who edited the project.
     */
    private String user_id;

    /**
     * A string representation of the project's overall progress (e.g., "75%", "Completed").
     */
    private String progress;

    /**
     * The name of the product associated with the project.
     */
    private String productName;

    /**
     * The category to which the product belongs.
     */
    private String productCategory;

    /**
     * The target platform of the product (e.g., Mobile, Web, IoT).
     */
    private String productPlatform;

    /**
     * The current development stage of the product (e.g., Prototype, MVP, Production).
     */
    private String currentStage;

    /**
     * The timestamp indicating when the project was first created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp indicating the last time the project was updated.
     */
    private LocalDateTime updateAt;
}