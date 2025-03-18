package com.api.growin.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.ProjectProduct;
import com.api.growin.models.ProjectProductService;

/**
 * Repository interface for managing {@link ProjectProductService} entities.
 * <p>
 *      This interface extends {@link JpaRepository}, providing built-in CRUD operations
 *      and additional query methods for retrieving product service based on different criteria.
 * </p>
 * 
 * <p>
 *      Annotated with {@code @Repository} to indicate that this interface is a Spring Data repository.
 *      The {@code @EnableJpaRepositories} annotation enables JPA repository support.
 * </p>
 */
@Repository
@EnableJpaRepositories
public interface ProjectProductServiceRepository extends JpaRepository <ProjectProductService, UUID> {
    /**
     * Retrieves all product service associated with a given product.
     *
     * @param product the product whose service are to be retrieved.
     * @return a list of {@link ProjectProductService} entities linked to the specified product.
     */
    Optional<ProjectProductService> findByProject(ProjectProduct project);
    
    /**
     * Retrieves an optional service based on the product ID.
     *
     * @param productId the UUID of the product.
     * @return an {@link Optional} containing the service if found, or empty if not found.
     */
    Optional<ProjectProductService> findByProjectId(UUID productId);
}
