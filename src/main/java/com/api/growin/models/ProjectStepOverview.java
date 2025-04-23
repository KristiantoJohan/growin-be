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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_step_overview")
public class ProjectStepOverview {
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
     * Logo of the product
     */
    @Builder.Default
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String logo = "";

    /**
     * Unique name of the product.
     */
    @Builder.Default
    @Column(unique = true, nullable = false)
    private String productName = "";

    /**
     * Short tagline that represents the product's core message.
     */
    @Builder.Default
    @Column(nullable = false)
    private String tagline = "";

    /**
     * Detailed description of the product, outlining its purpose and value.
     */
    @Builder.Default
    @Column(nullable = false)
    private String description = "";

    /**
     * Category of the product
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Category category = Category.UNDEFINED;

    /**
     * Current stage of the product
     */
    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrentStage stage = CurrentStage.DEFINE;

    /**
     * Platform of the product
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Platform platform = Platform.UNDEFINED;

    /**
     * URL to the product’s official website.
     */
    @Builder.Default
    @Column(nullable = false)
    private String websiteUrl = "";

    /**
     * Name of the team in charge of the product.
     */
    @Builder.Default
    @Column(nullable = false)
    private String teamInCharge = "";

    /**
     * Number of team members in the Hustler role.
     */
    @Builder.Default
    @Column(nullable = false)
    private Long hustler = 0L;

    /**
     * Number of team members in the Hipster role.
     */
    @Builder.Default
    @Column(nullable = false)
    private Long hipster = 0L;

    /**
     * Number of team members in the Hacker role.
     */
    @Builder.Default
    @Column(nullable = false)
    private Long hacker = 0L;

    /**
     * Name of the team leader managing the product team.
     */
    @Builder.Default
    @Column(nullable = false)
    private String teamLeader = "";

    /**
     * Email address of the team leader.
     */
    @Builder.Default
    @Column(nullable = false)
    private String email = "";

    /**
     * Phone number of the team leader.
     */
    @Builder.Default
    @Column(nullable = false)
    private String phone = "";

    /**
     * Vision statement of the product.
     */
    @Builder.Default
    @Column(nullable = false)
    private String productVision = "";

    /**
     * Mission statement of the product.
     */
    @Builder.Default
    @Column(nullable = false)
    private String productMission = "";

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