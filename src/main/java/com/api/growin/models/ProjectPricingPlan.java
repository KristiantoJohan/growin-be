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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_pricing_plan")
public class ProjectPricingPlan {
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
     * Target price by strategy value.
     */
    @Builder.Default
    @Column(nullable = false)
    private Long targetPriceStrategy = 0L;
    
    /**
     * Target price by benchmark value.
     */
    @Builder.Default
    @Column(nullable = false)
    private Long targetPriceBenchmark = 0L;
    
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