package com.api.growin.dto.response.projects;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A comprehensive response DTO representing the full details of a project.
 * <p>
 *     This class encapsulates various components of a project, including
 *     basic metadata, project overview, gallery images, and compliance documents.
 *     It is used primarily to display or fetch complete information related to
 *     a specific project in a structured and detailed manner.
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
public class ProjectDetailsResponse {

    /**
     * The unique identifier of the initialized product.
     */
    private String id;

    /**
     * Identifier for the last user who edited the project.
     */
    private String user_id;

    /**
     * The current progress of the overall project, 
     * typically represented as a percentage or a status string.
     */
    private String progress;

    /**
     * Nested DTO containing an overview of the product in the project.
     * This includes basic descriptive fields such as name, vision, team, and category.
     */
    private ProjectOverviewResponse projectOverview;

    /**
     * A list of gallery items (images) associated with the project.
     * Each item in the list represents a file uploaded to visually represent the project.
     */
    private List<ProjectGalleryResponse> projectGallery;

    /**
     * A list of compliance-related documents submitted for the project.
     * These documents help ensure legal, regulatory, or partnership compliance.
     */
    private List<ProjectComplienceDocumentsResponse> projectComplienceDocuments;

    /**
     * Nested DTO containing the pricing plan of the product in the project.
     * This includes the target pricing based on its strategy and its benchmark.
     */
    private ProjectPricingPlanResponse projectPricingPlan;

    /**
     * The timestamp indicating when the project was created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp indicating the last time the project was updated.
     */
    private LocalDateTime updateAt;
}