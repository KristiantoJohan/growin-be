package com.api.growin.dto.request.projects;

import org.springframework.web.multipart.MultipartFile;

import com.api.growin.utils.ObjectConstraint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload for submitting or updating a project overview.
 * <p>
 *     This DTO captures detailed information about a project, such as its identity,
 *     team structure, business goals, and compliance-related data. It supports optional
 *     file uploads and includes validation for specific fields.
 * </p>
 *
 * <p>
 *     This class leverages Lombok for boilerplate code generation and Jakarta Bean Validation
 *     annotations to enforce input constraints.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectOverviewRequest {

    /**
     * Logo representing the product.
     * <p>
     *     Optional image file upload. The file must be of type JPG or PNG
     *     and not exceed 10MB in size.
     * </p>
     */
    @ObjectConstraint.Rules(required = false, maxSize = 10 * 1024 * 1024, allowedTypes = {"image/jpg", "image/jpeg", "image/png"})
    private MultipartFile logo;

    /**
     * Name of the product.
     * <p>
     *     Must be at least 3 characters long.
     * </p>
     */
    @Size(min = 3, message = "Minimum character 3")
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
     * Category of the product
     */
    private String category;

    /**
     * Current stage of the product
     */
    private String stage;

    /**
     * Platform of the product
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
     * Number of team members categorized under the 'Hustler' role (business/marketing).
     * <p>Minimum value is 0.</p>
     */
    @Min(value = 0, message = "Minimum value is 0")
    private Long hustler;

    /**
     * Number of team members in the Hipster role.
     * <p>Minimum value is 0.</p>
     */
    @Min(value = 0, message = "Minimum value is 0")
    private Long hipster;

    /**
     * Number of team members in the Hacker role.
     * <p>Minimum value is 0.</p>
     */
    @Min(value = 0, message = "Minimum value is 0")
    private Long hacker;

    /**
     * Name of the team leader managing the product team.
     */
    private String teamLeader;

    /**
     * Email address of the team leader.
     * <p>Must follow a valid email format.</p>
     */
    @Email(message = "Invalid email format")
    private String email;

    /**
     * Phone number of the team leader.
     */
    private String phone;

    /**
     * Vision statement of the product.
     */
    private String productVision;

    /**
     * Mission statement of the product.
     */
    private String productMission;
}