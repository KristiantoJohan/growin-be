package com.api.growin.dto.response.projects;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO representing an overview of a project product.
 * <p>
 *     This class is used to return a detailed overview of the product,
 *     including its core attributes, documents, and team details.
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
public class ProjectOverviewResponse {

    /**
     * Unique identifier for the product.
     */
    private String id;

    /**
     * The logo of the product in the project.
     * <p> This field will contain the URL or path to the product's logo image. </p>
     */
    private String logo;

    /**
     * Unique name of the product.
     */
    private String productName;

    /**
     * Short tagline that represents the product's core message.
     */
    private String tagline;

    /**
     * Detailed description of the product, outlining its purpose and value.
     */
    private String description;

    /**
     * Category of the product.
     * <p> Examples: Mobile App, Web App, Physical Product </p>
     */
    private String category;

    /**
     * Current stage of the product.
     * <p> Examples: Concept, Development, Production </p>
     */
    private String stage;

    /**
     * Platform of the product.
     * <p> Examples: iOS, Android, Web </p>
     */
    private String platform;

    /**
     * URL to the product’s official website.
     */
    private String websiteUrl;

    /**
     * Name of the team in charge of the product.
     */
    private String teamInCharge;

    /**
     * Number of team members in the Hustler role.
     * <p> Hustlers are responsible for the business aspect of the product. </p>
     */
    private Long hustler;

    /**
     * Number of team members in the Hipster role.
     * <p> Hipsters are responsible for the design aspect of the product. </p>
     */
    private Long hipster;

    /**
     * Number of team members in the Hacker role.
     * <p> Hackers are responsible for the technical development of the product. </p>
     */
    private Long hacker;

    /**
     * Name of the team leader managing the product team.
     */
    private String teamLeader;

    /**
     * Email address of the team leader.
     */
    private String email;

    /**
     * Phone number of the team leader.
     */
    private String phone;

    /**
     * Vision statement of the product.
     * <p> Describes the long-term goals and aspirations of the product. </p>
     */
    private String productVision;

    /**
     * Mission statement of the product.
     * <p> Describes the current focus and objectives of the product. </p>
     */
    private String productMission;

    /**
     * URL or path to the legal compliance document.
     */
    private String legalComplience;

    /**
     * Original file name of the legal compliance document.
     */
    private String legalComplienceOriginalName;

    /**
     * URL or path to the privacy policy document.
     */
    private String privacyPolicy;

    /**
     * Original file name of the privacy policy document.
     */
    private String privacyPolicyOriginalName;

    /**
     * URL or path to the regulatory approval document.
     */
    private String regulatoryApproval;

    /**
     * Original file name of the regulatory approval document.
     */
    private String regulatoryApprovalOriginalName;

    /**
     * URL or path to the service level agreement document.
     */
    private String serviceLevelAgreement;

    /**
     * Original file name of the service level agreement document.
     */
    private String serviceLevelAgreementOriginalName;

    /**
     * URL or path to the data processing agreement document.
     */
    private String dataProcessingAgreement;

    /**
     * Original file name of the data processing agreement document.
     */
    private String dataProcessingAgreementOriginalName;

    /**
     * URL or path to the third-party compliance document.
     */
    private String thirdPartyComplience;

    /**
     * Original file name of the third-party compliance document.
     */
    private String thirdPartyComplienceOriginalName;

    /**
     * Timestamp indicating when the product overview was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update of the product overview.
     */
    private LocalDateTime updatedAt;
}