package com.api.growin.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.ProjectProduct;
import com.api.growin.models.ProjectProductComplienceDocuments;
import com.api.growin.models.ProjectProductTeamHighlight;

@Repository
@EnableJpaRepositories
public interface ProjectProductComplienceDocumentsRepository extends JpaRepository <ProjectProductComplienceDocuments, UUID> {
    /**
     * Retrieves all product complience documents associated with a given product.
     *
     * @param product the product whose complience documents are to be retrieved.
     * @return a list of {@link ProjectProductTeamHighlight} entities linked to the specified product.
     */
    Optional<ProjectProductComplienceDocuments> findByProject(ProjectProduct project);
    
    /**
     * Retrieves an optional complience documents based on the product ID.
     *
     * @param productId the UUID of the product.
     * @return an {@link Optional} containing the complience documents if found, or empty if not found.
     */
    Optional<ProjectProductComplienceDocuments> findByProjectId(UUID productId);
}
