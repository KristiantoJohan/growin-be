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
@Table(name = "project_product_service")
public class ProjectProductService {
    /**
     * Unique ID of product_service
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * One to one relation with product
     */
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProjectProduct project;

    /**
     * Define the vision of the product
     */
    @Column(columnDefinition = "LONGTEXT")
    private String vision;

    /**
     * Define the mission of the product
     */
    @Column(columnDefinition = "LONGTEXT")
    private String mission;

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