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
 * Entity representing a project gallery image in the system.
 * <p>
 *     This class maps to the {@code project_gallery} table in the database and stores
 *     information about individual gallery entries (such as image URLs) that are associated
 *     with a specific project. Each project can have one gallery image per entry.
 * </p>
 *
 * <p>
 *     This model includes metadata for auditing purposes, such as creation and update timestamps.
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
@Table(name = "project_gallery")
public class ProjectGallery {

    /**
     * Unique identifier for each gallery item.
     * <p>
     *     Automatically generated using UUID strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The project associated with this gallery item.
     * <p>
     *     This is a one-to-one relationship with the {@link Project} entity.
     *     Each gallery record must be linked to exactly one project.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /**
     * The URL or storage path of the gallery image.
     * <p>
     *     This may be a local path or a remote S3 bucket URL, depending on the storage strategy.
     * </p>
     */
    private String url;

    /**
     * Timestamp indicating when this gallery record was created.
     * <p>
     *     Automatically populated when the entity is persisted for the first time.
     * </p>
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last time this gallery record was updated.
     * <p>
     *     Automatically updated whenever the entity is modified.
     * </p>
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}