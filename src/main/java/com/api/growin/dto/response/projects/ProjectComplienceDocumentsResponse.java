package com.api.growin.dto.response.projects;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Data Transfer Object (DTO) representing a compliance document associated with a project.
 * <p>
 *     This response is typically used to return information about individual
 *     compliance-related documents such as privacy policies, legal agreements,
 *     or regulatory approvals that are required or submitted as part of the project.
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
public class ProjectComplienceDocumentsResponse {

    /**
     * The unique identifier of the compliance document.
     */
    private String id;

    /**
     * Type of the document.
     * <p>
     *     Available types include:
     *     <ul>
     *         <li>Legal Compliance</li>
     *         <li>Privacy Policy</li>
     *         <li>Regulatory Approval</li>
     *         <li>Service Level Agreement</li>
     *         <li>Data Processing Agreement</li>
     *         <li>Third-party Compliance</li>
     *     </ul>
     * </p>
     */
    private String document;

    /**
     * The URL or file path where the compliance document can be accessed.
     */
    private String documentUrl;

    /**
     * The original filename of the uploaded document.
     */
    private String documentOriginalName;

    /**
     * The timestamp indicating when the document record was created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp indicating the last time the document record was updated.
     */
    private LocalDateTime updatedAt;
}