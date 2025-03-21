package com.api.growin.dto.request.pm.project.overview;

import com.api.growin.models.Category;
import com.api.growin.models.CurrentStage;
import com.api.growin.models.Platform;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for creating a product's general information.
 * <p>
 *      This class is used to capture user input when creating a new product,
 *      including product ID, name, tagline, and description. It also includes
 *      validation constraints to ensure data integrity.
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
public class GeneralInfoJSONRequest {

    /** 
     * The name of the product.
     * <p>
     *      This field must not be blank.
     * </p>
     */
    @NotBlank(message = "name must not be null")
    private String name;

    /** 
     * A short and catchy tagline for the product.
     * <p>
     *      This field must not be blank.
     * </p>
     */
    @NotBlank(message = "tagline must not be null")
    private String tagline;

    /** 
     * A detailed description of the product.
     * <p>
     *      This field must not be blank.
     * </p>
     */
    @NotBlank(message = "description must not be null")
    private String description;

    /** 
     * the category of the product.
     * <p>
     *      This field must not be blank.
     * </p>
     */
    @NotNull(message = "Category cannot be null")
    private Category category;

    /** 
     * The current stage of the product.
     * <p>
     *      This field must not be blank.
     * </p>
     */
    @NotNull(message = "Current Stage cannot be null")
    private CurrentStage currentStage;

    /** 
     * The platform which the product run.
     * <p>
     *      This field must not be blank.
     * </p>
     */
    @NotNull(message = "platform cannot be null")
    private Platform platform;

    /** 
     * Website URL of the product.
     * <p>
     *      This field must not be blank.
     * </p>
     */
    private String websiteUrl;
}
