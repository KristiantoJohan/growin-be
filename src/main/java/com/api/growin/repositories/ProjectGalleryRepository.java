package com.api.growin.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.Project;
import com.api.growin.models.ProjectGallery;

/**
 * Repository interface for managing {@link ProjectGallery} entities.
 * <p>
 *     This repository provides standard CRUD operations as well as custom query
 *     methods for accessing project gallery data stored in the {@code project_gallery} table.
 * </p>
 *
 * <p>
 *     It extends {@link JpaRepository} to leverage Spring Data JPA's capabilities
 *     such as pagination, sorting, and query derivation.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Repository
@EnableJpaRepositories
public interface ProjectGalleryRepository extends JpaRepository<ProjectGallery, UUID> {

    /**
     * Retrieves all gallery entries associated with the specified project.
     *
     * @param project the {@link Project} entity whose gallery images are to be retrieved.
     * @return a list of {@link ProjectGallery} entities linked to the given project.
     */
    List<ProjectGallery> findByProject(Project project);
}