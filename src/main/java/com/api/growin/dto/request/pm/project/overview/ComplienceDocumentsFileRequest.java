package com.api.growin.dto.request.pm.project.overview;

import org.springframework.web.multipart.MultipartFile;

import com.api.growin.utils.ObjectConstraint;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for submitting compliance documents.
 * <p>
 *     This class handles the upload of multiple compliance-related documents,
 *     ensuring that each file meets specific validation rules such as file size 
 *     and type constraints.
 * </p>
 *
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-03-17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplienceDocumentsFileRequest {

    /** 
     * Legal compliance document.
     * <p>
     *     Must be a PDF file with a maximum size of 10MB.
     * </p>
     */
    @NotNull(message = "File cannot be empty")
    @ObjectConstraint.Rules(maxSize = 10 * 1024 * 1024, allowedTypes = {"application/pdf"})
    MultipartFile legalComplience;

    /** 
     * Privacy policy document.
     * <p>
     *     Must be a PDF file with a maximum size of 5MB.
     * </p>
     */
    @NotNull(message = "File cannot be empty")
    @ObjectConstraint.Rules(maxSize = 5 * 1024 * 1024, allowedTypes = {"application/pdf"})
    MultipartFile privacyPolicy;

    /** 
     * Regulatory approval document.
     * <p>
     *     Must be a PDF file with a maximum size of 5MB.
     * </p>
     */
    @NotNull(message = "File cannot be empty")
    @ObjectConstraint.Rules(maxSize = 5 * 1024 * 1024, allowedTypes = {"application/pdf"})
    MultipartFile regulatoryApproval;

    /** 
     * Service Level Agreement (SLA) document.
     * <p>
     *     Must be a PDF file with a maximum size of 5MB.
     * </p>
     */
    @NotNull(message = "File cannot be empty")
    @ObjectConstraint.Rules(maxSize = 5 * 1024 * 1024, allowedTypes = {"application/pdf"})
    MultipartFile serviceLevelAggrement;

    /** 
     * Data Processing Agreement (DPA) document.
     * <p>
     *     Must be a PDF file with a maximum size of 5MB.
     * </p>
     */
    @NotNull(message = "File cannot be empty")
    @ObjectConstraint.Rules(maxSize = 5 * 1024 * 1024, allowedTypes = {"application/pdf"})
    MultipartFile dataProcessingAggremeent;

    /** 
     * Third-party compliance document.
     * <p>
     *     Must be a PDF file with a maximum size of 5MB.
     * </p>
     */
    @NotNull(message = "File cannot be empty")
    @ObjectConstraint.Rules(maxSize = 5 * 1024 * 1024, allowedTypes = {"application/pdf"})
    MultipartFile thirdPartyCompliance;
}
