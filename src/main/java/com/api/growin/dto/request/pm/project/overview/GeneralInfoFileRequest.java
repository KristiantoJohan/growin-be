package com.api.growin.dto.request.pm.project.overview;

import org.springframework.web.multipart.MultipartFile;

import com.api.growin.utils.ObjectConstraint;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for handling file uploads related to general project information.
 * <p>
 *     This class is used to capture file input for project overview images. It ensures that 
 *     the uploaded file is not null and meets the required size and format constraints.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
public class GeneralInfoFileRequest {

    /**
     * The uploaded file for general project information.
     * <p>
     *     The file must not be empty and must adhere to the specified constraints:
     *     <ul>
     *         <li> Maximum size: 5MB </li>
     *         <li> Allowed formats: JPEG, PNG, JPG </li>
     *     </ul>
     * </p>
     */
    @NotNull(message = "File cannot be empty")
    @ObjectConstraint.Rules(maxSize = 5 * 1024 * 1024, allowedTypes = {"image/jpeg", "image/png", "image/jpg"})
    private MultipartFile file;
}
