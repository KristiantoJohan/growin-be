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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing the STPD (Segmentation, Targeting, Positioning, Differentiation)
 * step of a project within the GrowIn system.
 * 
 * <p>This entity holds marketing-related metrics and descriptions such as market size (TAM, SAM, SOM),
 * positioning texts, and differentiation explanation.</p>
 *
 * <p>This entity is mapped to the <b>project_step_stpd</b> table in the database and has a one-to-one 
 * relationship with the {@link Project} entity.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_step_stpd")
public class ProjectStepStpd {
	/**
     * Unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * One To many relation with product
     */
    @OneToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    /**
     * Total Addressable Market (TAM) value.
     */
    @Builder.Default
    @Column(nullable = false)
    private Long tam = 0L;

    /**
     * Serviceable Available Market (SAM) value.
     */
    @Builder.Default
    @Column(nullable = false)
    private Long sam = 0L;

    /**
     * Serviceable Obtainable Market (SOM) value.
     */
    @Builder.Default
    @Column(nullable = false)
    private Long som = 0L;

    /**
     * Text describing the overall market size.
     */
    @Builder.Default
    @Column(nullable = false)
    private String marketSizeDescription = "";

    /**
     * Positioning text - point one.
     */
    @Builder.Default
    @Column(nullable = false)
    private String positioningTextOne = "";

    /**
     * Positioning text - point two.
     */
    @Builder.Default
    @Column(nullable = false)
    private String positioningTextTwo = "";

    /**
     * Positioning text - point three.
     */
    @Builder.Default
    @Column(nullable = false)
    private String positioningTextThree = "";

    /**
     * Positioning text - point four.
     */
    @Builder.Default
    @Column(nullable = false)
    private String positioningTextFour = "";

    /**
     * Positioning text - point five.
     */
    @Builder.Default
    @Column(nullable = false)
    private String positioningTextFive = "";

    /**
     * Positioning text - point six.
     */
    @Builder.Default
    @Column(nullable = false)
    private String positioningTextSix = "";

    /**
     * Positioning text - point seven.
     */
    @Builder.Default
    @Column(nullable = false)
    private String positioningTextSeven = "";

    /**
     * Description of the product/service differentiation.
     */
    @Builder.Default
    @Column(nullable = false)
    private String differentiationDescription = "";
    
    /**
     * Timestamp indicating when the user was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update of user details.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
