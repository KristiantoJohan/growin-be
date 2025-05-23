package com.api.growin.services.projects;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.api.growin.dto.response.GeneralResponse;
import com.api.growin.dto.response.projects.*;
import com.api.growin.models.*;
import com.api.growin.repositories.*;
import org.springframework.stereotype.Service;

import com.api.growin.exceptions.ProjectNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectStepOverviewRepository projectStepOverviewRepository;
    private final ProjectStepStpdRepository projectStepStpdRepository;
    private final ProjectPricingPlanRepository projectPricingPlanRepository;

    /**
     * Initializes a new project for the specified user.
     * <p>
     *     This method creates a new {@link Project} associated with the given user.
     *     If the user does not exist, an {@link EntityNotFoundException} is thrown.
     *     The newly created project is then saved in the database, and a response containing
     *     project details is returned.
     * </p>
     *
     * @param userId the unique identifier of the user initializing the project
     * @return a {@link ProjectInitResponse} containing details of the newly created project
     * @throws EntityNotFoundException if no user is found with the given ID
     */
    public ProjectInitResponse projectInit(String userId) {
        /* Find the user with corresponding request.user_id */
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new EntityNotFoundException("Invalid user"));

        /* Initialize new main data project and sub data of the project */
        Project newProduct = Project.builder()
            .user(user)
            .build();
        
        ProjectStepOverview newStepOverview = ProjectStepOverview.builder()
            .project(newProduct)
            .build();
        
        ProjectStepStpd newStepStpd = ProjectStepStpd.builder()
            .project(newProduct)
            .build();

        ProjectPricingPlan newProjectPricingPlan = ProjectPricingPlan.builder()
            .project(newProduct)
            .build();

        /* Save to database */
        newProduct = projectRepository.save(newProduct);
        newStepOverview = projectStepOverviewRepository.save(newStepOverview);
        newStepStpd = projectStepStpdRepository.save(newStepStpd);
        newProjectPricingPlan = projectPricingPlanRepository.save(newProjectPricingPlan);
        
        /* Sending the response */
        return ProjectInitResponse.builder()
            .id(newProduct.getId().toString())
            .user_id(user.getId().toString())
            .createdAt(newProduct.getCreatedAt())
            .updateAt(newProduct.getUpdatedAt())
            .build();
    }

    /**
     * Retrieves all projects associated with the specified user.
     * <p>
     *     This method looks up a {@link User} by their unique ID. If the user exists,
     *     it fetches all {@link Project} entities tied to that user and maps each project
     *     into a simplified {@link ProjectBriefResponse} DTO, which includes essential
     *     project metadata such as product name, category, platform, and timestamps.
     *     If the user is not found, an {@link EntityNotFoundException} is thrown.
     * </p>
     *
     * @param userId the unique identifier of the user whose projects are being retrieved
     * @return a list of {@link ProjectBriefResponse} objects representing each project
     * @throws EntityNotFoundException if no user is found with the specified ID
     */
    public List<ProjectBriefResponse> getAllProjectsOfUser(String userId) {
        /* Find the user with corresponding request.user_id */
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new EntityNotFoundException("Invalid user"));

        /* Get all projects with the corresponding userId */
        List<Project> getAllProject = projectRepository.findByUser(user);
        
        /* Map fetched project to DTO */
        return getAllProject.stream()
            .map(project -> ProjectBriefResponse.builder()
                .id(project.getId().toString())
                .user_id(user.getId().toString())
                .progress(project.getProgress().toString())
                .productName(project.getProjectStepOverviews().getProductName())
                .productCategory(project.getProjectStepOverviews().getCategory().toString())
                .productPlatform(project.getProjectStepOverviews().getPlatform().toString())                
                .createdAt(project.getCreatedAt())
                .updateAt(project.getUpdatedAt())
                .build())
            .collect(Collectors.toList());
    }

    /**
     * Retrieves detailed information for a specific project by its ID.
     * <p>
     *     This method searches for a {@link Project} using the provided ID. If the project exists,
     *     it composes a detailed {@link ProjectDetailsResponse} containing both the main project
     *     data and its associated overview, such as team composition, platform, category, vision,
     *     mission, and compliance document URLs and names. It also includes a list of associated
     *     gallery images with their respective metadata.
     * </p>
     *
     * @param id the unique identifier of the project to retrieve
     * @return a fully populated {@link ProjectDetailsResponse} representing the project's overview and galleries
     * @throws ProjectNotFoundException if no project is found with the specified ID
     */
    public ProjectDetailsResponse getDetailsProjectById(String id) {
        /* Get the project with the corresponding id  */
        Project project = projectRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ProjectNotFoundException("Selected project not found"));

        return ProjectDetailsResponse.builder()
            .id(project.getId().toString())
            .user_id(project.getUser().getId().toString())
            .progress(project.getProgress().toString())
            .projectOverview(ProjectOverviewResponse.builder()
                .id(project.getProjectStepOverviews().getId().toString())
                .logo(project.getProjectStepOverviews().getLogo())
                .productName(project.getProjectStepOverviews().getProductName())
                .tagline(project.getProjectStepOverviews().getTagline())
                .description(project.getProjectStepOverviews().getDescription())
                .category(project.getProjectStepOverviews().getCategory().toString())
                .stage(project.getProjectStepOverviews().getStage().toString())
                .platform(project.getProjectStepOverviews().getPlatform().toString())
                .websiteUrl(project.getProjectStepOverviews().getWebsiteUrl())
                .teamInCharge(project.getProjectStepOverviews().getTeamInCharge())
                .hustler(project.getProjectStepOverviews().getHustler())
                .hipster(project.getProjectStepOverviews().getHipster())
                .hacker(project.getProjectStepOverviews().getHacker())
                .teamLeader(project.getProjectStepOverviews().getTeamLeader())
                .email(project.getProjectStepOverviews().getEmail())
                .phone(project.getProjectStepOverviews().getPhone())
                .productVision(project.getProjectStepOverviews().getProductVision())
                .productMission(project.getProjectStepOverviews().getProductMission())
                .createdAt(project.getProjectStepOverviews().getCreatedAt())
                .updatedAt(project.getProjectStepOverviews().getUpdatedAt())
                .build()
            )
            .projectGallery(project.getProjectGalleries()
                .stream()
                .map(gallery -> ProjectGalleryResponse.builder()
                    .id(gallery.getId().toString())
                    .url(gallery.getUrl())
                    .createdAt(gallery.getCreatedAt())
                    .updateAt(gallery.getUpdatedAt())
                    .build()
                ).collect(Collectors.toList())
            )
            .projectComplienceDocuments(project.getProjectComplienceDocuments()
                .stream()
                .map(complienceDocuments -> ProjectComplienceDocumentsResponse.builder()
                    .id(complienceDocuments.getId().toString())
                    .documentUrl(complienceDocuments.getDocumentUrl())
                    .document(complienceDocuments.getDocument())
                    .documentOriginalName(complienceDocuments.getDocumentOriginalName())
                    .createdAt(complienceDocuments.getCreatedAt())
                    .updatedAt(complienceDocuments.getUpdatedAt())
                    .build()
                ).collect(Collectors.toList())
            )
            .projectPricingPlan(ProjectPricingPlanResponse.builder()
                .id(project.getProjectPricingPlan().getId().toString())
                .targetPriceStrategy(project.getProjectPricingPlan().getTargetPriceStrategy())
                .targetPriceBenchmark(project.getProjectPricingPlan().getTargetPriceBenchmark())
                .createdAt(project.getProjectPricingPlan().getCreatedAt())
                .updatedAt(project.getProjectPricingPlan().getUpdatedAt())
                .build()
            )
        .build();
    }

    public GeneralResponse deleteProject(String projectId) {
        /* Get the project with the corresponding id  */
        Project project = projectRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectNotFoundException("Selected project not found"));

        /* Save to database */
        projectStepOverviewRepository.deleteByProject(project);
        projectStepStpdRepository.deleteByProject(project);
        projectPricingPlanRepository.deleteByProject(project);
        projectRepository.delete(project);


        /* Sending the response */
        return GeneralResponse.builder()
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
}