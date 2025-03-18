package com.api.growin.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.ProjectProduct;
import com.api.growin.models.ProjectProductTeamHighlight;

/**
 * Repository interface for managing {@link ProjectProductTeamHighlight} entities.
 * <p>
 *      This interface extends {@link JpaRepository}, providing built-in CRUD operations
 *      and additional query methods for retrieving product team highlight based on different criteria.
 * </p>
 * 
 * <p>
 *      Annotated with {@code @Repository} to indicate that this interface is a Spring Data repository.
 *      The {@code @EnableJpaRepositories} annotation enables JPA repository support.
 * </p>
 */
@Repository
@EnableJpaRepositories
public interface ProjectProductTeamHighlightRepository extends JpaRepository <ProjectProductTeamHighlight, UUID> {

    /**
     * Retrieves all product team highlight associated with a given product.
     *
     * @param product the product whose team highlight are to be retrieved.
     * @return a list of {@link ProjectProductTeamHighlight} entities linked to the specified product.
     */
    Optional<ProjectProductTeamHighlight> findByProject(ProjectProduct project);
    
    /**
     * Retrieves an optional team highlight based on the product ID.
     *
     * @param productId the UUID of the product.
     * @return an {@link Optional} containing the team highlight if found, or empty if not found.
     */
    Optional<ProjectProductTeamHighlight> findByProjectId(UUID productId);
}
