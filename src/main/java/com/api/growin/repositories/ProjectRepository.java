package com.api.growin.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.api.growin.models.Project;
import com.api.growin.models.User;

/**
 * Repository interface for managing {@link Project} entities.
 * <p>
 *      This interface extends {@link JpaRepository}, providing built-in CRUD operations
 *      and additional query methods for retrieving Project based on different criteria.
 * </p>
 *
 * <p>
 *      Annotated with {@code @Repository} to indicate that this interface is a Spring Data repository.
 *      The {@code @EnableJpaRepositories} annotation enables JPA repository support.
 * </p>
 */
@Repository
@EnableJpaRepositories
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    /**
     * Retrieves all projects associated with a given user.
     *
     * @param user the user whose projects are to be retrieved.
     * @return a list of {@link Project} entities linked to the specified user.
     */
    List<Project> findByUser(User user);

}