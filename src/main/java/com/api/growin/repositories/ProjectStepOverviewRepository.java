package com.api.growin.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.Project;
import com.api.growin.models.ProjectStepOverview;

/**
 * Repository interface for managing {@link ProjectStepOverview} entities.
 * <p>
 *     Provides CRUD operations and custom query methods to interact with the
 *     {@link ProjectStepOverview} data in the database.
 * </p>
 *
 * @see JpaRepository
 */
@Repository
@EnableJpaRepositories
public interface ProjectStepOverviewRepository extends JpaRepository<ProjectStepOverview, UUID> {

    /**
     * Finds the project step overview associated with the given project.
     *
     * @param project The project entity used as a reference.
     * @return The {@link ProjectStepOverview} associated with the specified project.
     */
    ProjectStepOverview findByProject(Project project);
}