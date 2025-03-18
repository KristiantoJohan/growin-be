package com.api.growin.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

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
 * Represents a product in the system.
 * <p>
 *      Implements {@link UserDetails} to integrate with Spring Security authentication.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_product_general_information")
public class ProjectProductGeneralInformation {

    /**
     * Unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     *  One To One relation with product
     */
    @OneToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectProduct project;

    /**
     * Unique name for the product.
     */
    @Column(unique = true)
    private String name;

    /**
     * tagline for the product.
     */
    private String tagline;

    /**
     * description for the product.
     */
    private String description;

    /**
     * URL of the product logo.
     */
    private String logo;

    /**
     * Category assigned to the product, determining their permissions.
     */
    @Enumerated(EnumType.STRING)
    private Category category;

    /**
     * CurrentStage assigned to the product, determining their permissions.
     */
    @Enumerated(EnumType.STRING)
    private CurrentStage currentStage;

    /**
     * Platform assigned to the product, determining their permissions.
     */
    @Enumerated(EnumType.STRING)
    private Platform platform;

    /**
     *  Website of the product
     */
    private String websiteUrl;

    /**
     * Timestamp indicating when the product was created.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update of product.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
