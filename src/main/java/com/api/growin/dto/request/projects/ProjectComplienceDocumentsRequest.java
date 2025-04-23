package com.api.growin.dto.request.projects;

import org.springframework.web.multipart.MultipartFile;

import com.api.growin.utils.ObjectConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload for updating project compliance documents.
 * <p>
 *     This data transfer object (DTO) is used to carry multipart file uploads
 *     and metadata related to a project's compliance documentation. It typically
 *     includes fields such as legal compliance, privacy policy, regulatory approvals,
 *     service level agreements, data processing agreements, and third-party compliance.
 * </p>
 *
 * <p>
 *     This class uses Lombok annotations to automatically generate boilerplate code
 *     such as getters, setters, constructors, and the builder pattern.
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
public class ProjectComplienceDocumentsRequest {
    /**
     * URL or path to the legal compliance document.
     */
    @ObjectConstraint.Rules(required = false, maxSize = 10 * 1024 * 1024, allowedTypes = {"application/pdf"})
    private MultipartFile legalComplience;

    /**
     * URL or path to the privacy policy document.
     */
    @ObjectConstraint.Rules(required = false, maxSize = 10 * 1024 * 1024, allowedTypes = {"application/pdf"})
    private MultipartFile privacyPolicy;

    /**
     * URL or path to the regulatory approval document.
     */
    @ObjectConstraint.Rules(required = false, maxSize = 10 * 1024 * 1024, allowedTypes = {"application/pdf"})
    private MultipartFile regulatoryApproval;

    /**
     * URL or path to the service level agreement document.
     */
    @ObjectConstraint.Rules(required = false, maxSize = 10 * 1024 * 1024, allowedTypes = {"application/pdf"})
    private MultipartFile serviceLevelAgreement;

    /**
     * URL or path to the data processing agreement document.
     */
    @ObjectConstraint.Rules(required = false, maxSize = 10 * 1024 * 1024, allowedTypes = {"application/pdf"})
    private MultipartFile dataProcessingAgreement;

    /**
     * URL or path to the third-party compliance document.
     */
    @ObjectConstraint.Rules(required = false, maxSize = 10 * 1024 * 1024, allowedTypes = {"application/pdf"})
    private MultipartFile thirdPartyComplience;
}