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
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectProduct project;

    /**
     * Type of the document.
     * <p>
     *     Available type:
     *     <ul>
     *          <li> Legal Complience </li>
     *          <li> Privacy Policy </li>
     *          <li> Regualatory Approval </li>
     *          <li> Service Level Aggreement </li>
     *          <li> Data Processing Aggreement </li>
     *          <li> Third party complience </li>
     *      </ul> 
     * </p>
     */
    private String document;

    /**
     * Link for the complience document
     */
    @Column(columnDefinition = "LONGTEXT")
    private String documentUrl;

    /**
     * Link for the complience document
     */
    private String documentOriginalName;

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
