package com.api.growin.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.ProjectProduct;
import com.api.growin.models.ProjectProductGeneralInformation;

/**
 * Repository interface for managing {@link ProjectProductGeneralInformation} entities.
 * <p>
 *      This interface extends {@link JpaRepository}, providing built-in CRUD operations
 *      and additional query methods for retrieving product general information based on different criteria.
 * </p>
 * 
 * <p>
 *      Annotated with {@code @Repository} to indicate that this interface is a Spring Data repository.
 *      The {@code @EnableJpaRepositories} annotation enables JPA repository support.
 * </p>
 */
@Repository
@EnableJpaRepositories
public interface ProjectProductGeneralInformationRepository extends JpaRepository <ProjectProductGeneralInformation, UUID> {
    /**
     * Retrieves all product general information associated with a given product.
     *
     * @param product the product whose general informations are to be retrieved.
     * @return a list of {@link ProjectProductGeneralInformation} entities linked to the specified product.
     */
    Optional<ProjectProductGeneralInformation> findByProject(ProjectProduct project);
    
    /**
     * Retrieves an optional general information based on the product ID.
     *
     * @param productId the UUID of the product.
     * @return an {@link Optional} containing the general information if found, or empty if not found.
     */
    Optional<ProjectProductGeneralInformation> findByProjectId(UUID productId);

    /**
     * Retrieves an optional general information based on the product ID.
     *
     * @param productId the UUID of the product.
     * @return an {@link Optional} containing the general information if found, or empty if not found.
     */
    Optional<ProjectProductGeneralInformation> findByName(String name);
}
