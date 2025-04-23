package com.api.growin.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing compliance-related documents associated with a project.
 * <p>
 *     This class maps to the {@code project_complience_documents} table in the database and stores
 *     metadata and storage information for various compliance documents tied to a project.
 * </p>
 *
 * <p>
 *     Compliance documents may include:
 *     <ul>
 *         <li>Legal Compliance</li>
 *         <li>Privacy Policy</li>
 *         <li>Regulatory Approval</li>
 *         <li>Service Level Agreement</li>
 *         <li>Data Processing Agreement</li>
 *         <li>Third Party Compliance</li>
 *     </ul>
 * </p>
 *
 * <p>
 *     Each document is associated with a single project, allowing multiple documents to be linked to the same project.
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
@Entity
@Table(name = "project_complience_documents")
public class ProjectComplienceDocuments {

    /**
     * Unique identifier for each compliance document entry.
     * <p>
     *     Automatically generated using UUID strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The project associated with this compliance document.
     * <p>
     *     Many documents can be associated with the same project.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /**
     * The type or category of the compliance document.
     * <p>
     *     Examples include:
     *     <ul>
     *         <li>Legal Compliance</li>
     *         <li>Privacy Policy</li>
     *         <li>Regulatory Approval</li>
     *         <li>Service Level Agreement</li>
     *         <li>Data Processing Agreement</li>
     *         <li>Third Party Compliance</li>
     *     </ul>
     * </p>
     */
    private String document;

    /**
     * The URL or storage path where the compliance document is stored.
     * <p>
     *     This could be a public or private file location such as an S3 bucket path.
     * </p>
     */
    @Column(columnDefinition = "LONGTEXT")
    private String documentUrl;

    /**
     * The original file name of the uploaded compliance document.
     * <p>
     *     Useful for display purposes in the UI.
     * </p>
     */
    private String documentOriginalName;

    /**
     * Timestamp indicating when this document entry was created.
     * <p>
     *     Automatically populated on entity persistence.
     * </p>
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last time this document entry was modified.
     * <p>
     *     Automatically updated on every update operation.
     * </p>
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}