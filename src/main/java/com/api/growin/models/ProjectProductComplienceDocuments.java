package com.api.growin.models;

import java.util.UUID;

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
@Table(name = "project_product_complience_documents")
public class ProjectProductComplienceDocuments {
    /**
     * Unique ID of product complience documents
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     *  One To many relation with product
     */
    @OneToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectProduct project;

    /**
     * Link for the legal complience document
     */
    private String legalComplience;

    /**
     * Link for the privacy policy document
     */
    private String privacyPolicy;

    /**
     * Link for the regulatory approval document
     */
    private String regulatoryApproval;

    /**
     * Link for the service level aggreement document
     */
    private String serviceLevelAggrement;

    /**
     * Link for the data processing agreement document
     */
    private String dataProcessingAgreement;

    /**
     * Link for the third party compliance (ISO 27001) document
     */
    private String thirdPartyCompliance;
}
