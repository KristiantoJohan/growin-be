package com.api.growin.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.Project;
import com.api.growin.models.ProjectStepStpd;

@Repository
@EnableJpaRepositories
public interface ProjectStepStpdRepository extends JpaRepository<ProjectStepStpd, UUID>  {
	
	/**
     * Finds the project step stpd associated with the given project.
     *
     * @param project The project entity used as a reference.
     * @return The {@link ProjectStepStpd} associated with the specified project.
     */
    ProjectStepStpd findByProject(Project project);

	 void deleteByProject(Project project);

}
