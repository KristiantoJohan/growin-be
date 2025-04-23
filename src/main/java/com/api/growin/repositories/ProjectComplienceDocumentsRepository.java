package com.api.growin.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.Project;
import com.api.growin.models.ProjectComplienceDocuments;

@Repository
@EnableJpaRepositories
public interface ProjectComplienceDocumentsRepository extends JpaRepository<ProjectComplienceDocuments, UUID> {
    /**
     * Retrieves all product complience documents associated with a given product.
     *
     * @param product the product whose complience documents are to be retrieved.
     * @return a list of {@link ProjectComplienceDocuments} entities linked to the specified product.
     */
    List<ProjectComplienceDocuments> findByProject(Project project);
    
    /**
     * Retrieves an optional complience documents based on the product ID.
     *
     * @param productId the UUID of the product.
     * @return an {@link Optional} containing the complience documents if found, or empty if not found.
     */
    Optional<ProjectComplienceDocuments> findByProjectId(UUID productId);

    void deleteByProjectAndDocumentIn(Project project, List<String> updatedDocNames);
}