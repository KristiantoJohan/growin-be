package com.api.growin.controllers.projects;

import java.util.List;
import java.util.UUID;

import com.api.growin.dto.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.growin.dto.response.projects.ProjectBriefResponse;
import com.api.growin.dto.response.projects.ProjectDetailsResponse;
import com.api.growin.dto.response.projects.ProjectInitResponse;
import com.api.growin.models.User;
import com.api.growin.services.projects.ProjectService;
import com.api.growin.utils.HttpResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/v1/pm/project")
@RequiredArgsConstructor
public class ProjectsController {

    /**
     *  Initialize dependencies
     */
    @Autowired private final ProjectService projectService;

    /**
     * Initializes a new project for the authenticated user.
     * <p>
     *     This endpoint creates a new project associated with the currently 
     *     authenticated user and returns project initialization details.
     * </p>
     *
     * @param user The authenticated user obtained from the security context.
     * @return A response entity containing the project initialization response.
     */
    @PostMapping("/")    
    public ResponseEntity<Object> projectInit(@AuthenticationPrincipal User user) {
        /* Initialize new project with userId */
        ProjectInitResponse response = projectService.projectInit(user.getId().toString());

        /* Sending response */
        return HttpResponse.successResponse("Successfully initialize new project", response);
    }

    /**
     * Retrieves all projects associated with the authenticated user.
     * <p>
     *     This endpoint returns a list of brief project summaries that belong
     *     to the currently authenticated user.
     * </p>
     *
     * @param user The authenticated user obtained from the security context.
     * @return A response entity containing a list of project summaries.
     */
    @GetMapping("/")
    public ResponseEntity<Object> getProjects(@AuthenticationPrincipal User user) {
        /* Getting all projects of a user */
        List<ProjectBriefResponse> response = projectService.getAllProjectsOfUser(user.getId().toString());

        /* Sending response */
        return HttpResponse.successResponse("Successfully getting all projects", response);
    }
    
    /**
     * Retrieves detailed information about a specific project.
     * <p>
     *     This endpoint fetches complete details of a project identified by the given project ID.
     *     It includes project metadata, overview, associated documents, and gallery if available.
     *     If the project is not found, an appropriate exception is thrown.
     * </p>
     *
     * @param projectid The unique identifier of the project to be retrieved.
     * @return A response entity containing detailed information of the requested project.
     */
    @GetMapping("/{projectid}")
    public ResponseEntity<Object> getProjectsById(@PathVariable UUID projectid) {
        /* Getting the corresponding project */
        ProjectDetailsResponse response = projectService.getDetailsProjectById(projectid.toString());

        /* Sending response */
        return HttpResponse.successResponse("Successfully getting the projects", response);
    }

    @DeleteMapping("/{projectid}")
    public ResponseEntity<Object> deleteProject(@PathVariable UUID projectid) {
        /* Getting the corresponding project */
        GeneralResponse response = projectService.deleteProject(projectid.toString());

        /* Sending response */
        return HttpResponse.successResponse("Successfully getting the projects", response);
    }
}