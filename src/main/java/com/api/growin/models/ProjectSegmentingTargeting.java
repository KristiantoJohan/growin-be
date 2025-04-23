package com.api.growin.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing the segmentation and targeting step of a project in the GrowIn system.
 * 
 * <p>This model captures specific segmentation details such as the market segment name, 
 * characteristics, demand, needs fit, target flag, and additional notes for targeting decisions.</p>
 *
 * <p>This entity is mapped to the <b>project_segmenting_targeting</b> table in the database and 
 * has a one-to-one relationship with the {@link Project} entity.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_segmenting_targeting")
public class ProjectSegmentingTargeting {

    /**
     * Unique identifier for this segmenting and targeting record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * One-to-one association with the related project.
     */
    @OneToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /**
     * The market segment name (e.g., "Small Businesses", "Healthcare Sector").
     */
    private String segment;

    /**
     * Characteristics of the defined segment (e.g., demographics, behaviors).
     */
    private String characteristic;

    /**
     * The level of demand within the segment (e.g., HIGH, MEDIUM, LOW).
     * This is stored as an enum value.
     */
    @Enumerated(EnumType.STRING)
    private Demand demand;

    /**
     * How well the product fits the needs of this segment (e.g., PERFECT, GOOD, POOR).
     * This is stored as an enum value.
     */
    @Enumerated(EnumType.STRING)
    private NeedsFit needsFit;

    /**
     * Indicates whether this segment is a primary target for the project.
     */
    private Boolean target;

    /**
     * Additional notes or considerations for this segment.
     */
    private String note;

    /**
     * Timestamp indicating when the record was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update to this record.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

