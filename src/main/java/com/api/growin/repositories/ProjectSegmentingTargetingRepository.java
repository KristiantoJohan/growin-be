package com.api.growin.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.Project;
import com.api.growin.models.ProjectSegmentingTargeting;

@Repository
@EnableJpaRepositories
public interface ProjectSegmentingTargetingRepository extends JpaRepository<ProjectSegmentingTargeting, UUID>{
	/**
     * Retrieves all project segmenting targeting entries associated with the specified project.
     *
     * @param project the {@link Project} entity whose gallery images are to be retrieved.
     * @return a list of {@link ProjectSegmentingTargeting} entities linked to the given project.
     */
    List<ProjectSegmentingTargeting> findByProject(Project project);
}
