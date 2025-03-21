package com.api.growin.services.pm.projects;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.growin.dto.request.pm.project.overview.ComplienceDocumentsFileRequest;
import com.api.growin.dto.request.pm.project.overview.GeneralInfoJSONRequest;
import com.api.growin.dto.request.pm.project.overview.ServicesJSONRequest;
import com.api.growin.dto.request.pm.project.overview.TeamHighlightJSONRequest;
import com.api.growin.dto.response.GeneralResponse;
import com.api.growin.dto.response.pm.project.ProjectInitResponse;
import com.api.growin.exceptions.ProjectProductNotFoundException;
import com.api.growin.models.ProjectProduct;
import com.api.growin.models.ProjectProductComplienceDocuments;
import com.api.growin.models.ProjectProductGallery;
import com.api.growin.models.ProjectProductGeneralInformation;
import com.api.growin.models.ProjectProductService;
import com.api.growin.models.ProjectProductTeamHighlight;
import com.api.growin.models.User;
import com.api.growin.repositories.ProjectProductComplienceDocumentsRepository;
import com.api.growin.repositories.ProjectProductGalleryRepository;
import com.api.growin.repositories.ProjectProductGeneralInformationRepository;
import com.api.growin.repositories.ProjectProductRepository;
import com.api.growin.repositories.ProjectProductServiceRepository;
import com.api.growin.repositories.ProjectProductTeamHighlightRepository;
import com.api.growin.repositories.UserRepository;
import com.api.growin.utils.FileUploader;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Service class for managing project-related operations.
 * <p>
 *     This service provides business logic for handling various project-related
 *     functionalities, such as project creation, updates, and retrieval.
 *     It acts as an intermediary between the controller and the data repository.
 * </p>
 *
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-03-17
 */
@Service
@RequiredArgsConstructor
public class OverviewService {

    /* Repository */
    private final ProjectProductRepository projectProductRepository;
    private final ProjectProductGeneralInformationRepository projectProductGeneralInformationRepository;
    private final ProjectProductGalleryRepository projectProductGalleryRepository;
    private final ProjectProductTeamHighlightRepository projectProductTeamHighlightRepository;
    private final ProjectProductServiceRepository projectProductServiceRepository;
    private final ProjectProductComplienceDocumentsRepository projectProductComplienceDocumentsRepository;
    private final UserRepository userRepository;

    /* Utils */
    private final FileUploader fileUploader;

    /**
     * Initializes a new project for the specified user.
     * <p>
     *     This method creates a new {@link ProjectProduct} associated with the given user.
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
        /* Find user the user with corresponding request.user_id */
        User getUser = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new EntityNotFoundException("Invalid User ID"));

        /* Initialize new product */
        ProjectProduct newProduct = ProjectProduct.builder()
            .user(getUser)
            .build();

        /* Save to database */
        newProduct = projectProductRepository.save(newProduct);
        
        return ProjectInitResponse.builder()
            .id(newProduct.getId().toString())
            .user_id(userId)
            .createdAt(newProduct.getCreatedAt())
            .updateAt(newProduct.getUpdatedAt())
            .build();
    }

    /**
     * Creates a new general information entry for a project.
     * <p>
     *     This method validates the existence of the project associated with the provided project ID.
     *     It then uploads the project logo to S3 storage and initializes a new {@link ProjectProductGeneralInformation} 
     *     entity with the provided data. The newly created entry is saved in the database, and 
     *     a response containing its creation and update timestamps is returned.
     * </p>
     *
     * @param request the {@link GeneralInfoJSONRequest} containing project general information
     * @param logo the {@link MultipartFile} representing the project logo to be uploaded
     * @return a {@link GeneralResponse} containing the timestamps of the created entry
     * @throws EntityNotFoundException if no project is found with the given project ID
     */
    public GeneralResponse createProductGeneralInformation(String projectId, GeneralInfoJSONRequest request, MultipartFile logo) {
        /* Check if the project ID currently exist in database */
        ProjectProduct existingProjectProduct = projectProductRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectProductNotFoundException("Invalid project ID"));
        
        /* Upload file to S3 storage */
        String pathFolder = "projects/overview/general_information/";
        String fileUrl = fileUploader.uploadFile(pathFolder, logo);

        /* Initialize new product general information instance */
        ProjectProductGeneralInformation newProjectProductGeneralInformation = ProjectProductGeneralInformation.builder()
            .project(existingProjectProduct)
            .name(request.getName())
            .tagline(request.getTagline())
            .description(request.getDescription())
            .category(request.getCategory())
            .currentStage(request.getCurrentStage())
            .platform(request.getPlatform())
            .websiteUrl(request.getWebsiteUrl())
            .logo(fileUrl)
            .build();
        
        /* Save the product general information instance to database */
        newProjectProductGeneralInformation = projectProductGeneralInformationRepository.save(newProjectProductGeneralInformation);

        return GeneralResponse.builder()
            .createdAt(newProjectProductGeneralInformation.getCreatedAt())
            .updateAt(newProjectProductGeneralInformation.getUpdatedAt())
            .build();
    }

    /**
     * Creates a product gallery by uploading images for a given project.
     * <p>
     *     This method verifies the existence of the specified project, uploads 
     *     the provided images to S3 storage, and stores their URLs in the database.
     * </p>
     *
     * @param projectId The unique identifier of the project.
     * @param photos A list of images to be uploaded.
     * @return A {@link GeneralResponse} indicating the success of the operation.
     * @throws ProjectProductNotFoundException if the project ID does not exist.
     */
    public GeneralResponse createProductGallery(String projectId, List<MultipartFile> photos) {
        /* Check if the project ID currently exist in database */
        ProjectProduct existingProjectProduct = projectProductRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectProductNotFoundException("Invalid project ID"));

        /* Upload file to S3 storage */
        String pathFolder = "projects/overview/gallery/" + projectId;
        List<String> uploadedPhotoUrl = fileUploader.uploadFiles(pathFolder, photos);
        
        /* List for all new entities */
        List <ProjectProductGallery> projectProductGalleries = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            /* Initialize instances for each uploaded file */
            projectProductGalleries.add(
                ProjectProductGallery.builder()
                    .project(existingProjectProduct)
                    .image_url(uploadedPhotoUrl.get(i))
                    .build()    
            );
        }

        /* Save all entities to database */
        projectProductGalleries = projectProductGalleryRepository.saveAll(projectProductGalleries);

        return GeneralResponse.builder()
            .createdAt(LocalDateTime.now())
            .updateAt(LocalDateTime.now())
            .build();
    }

    /**
     * Creates and stores a new Product Team Highlight for a given project.
     * <p>
     *     This method verifies the existence of the project, initializes a new
     *     team highlight entity with the provided details, calculates the total number 
     *     of team members, and saves the entity to the database.
     * </p>
     *
     * @param projectId The unique identifier of the project for which the team highlight is being created.
     * @param request   The request object containing team highlight details, such as team composition and leadership.
     * @return A {@link GeneralResponse} containing timestamps of creation and update.
     * @throws ProjectProductNotFoundException if the specified project ID does not exist.
     */
    public GeneralResponse createProductTeamHighlight(String projectId, TeamHighlightJSONRequest request) {
        /* Check if the project ID currently exist in database */
        ProjectProduct existingProjectProduct = projectProductRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectProductNotFoundException("Invalid project ID"));

        /* Initialize product team highlight instance */
        ProjectProductTeamHighlight newProjectProductTeamHighlight = ProjectProductTeamHighlight.builder()
            .project(existingProjectProduct)
            .teamInCharge(request.getTeamInCharge())
            .hustler(request.getHustler())
            .hipster(request.getHipster())
            .hacker(request.getHacker())
            .teamMemberTotal(request.getHustler() + request.getHipster() + request.getHacker())
            .teamLeader(request.getTeamLeader())
            .teamLeaderEmail(request.getTeamLeaderEmail())
            .teamLeaderPhone(request.getTeamLeaderPhone())
            .build();
        
        /* Save the product team highlight instance to database */
        newProjectProductTeamHighlight = projectProductTeamHighlightRepository.save(newProjectProductTeamHighlight);

        return GeneralResponse.builder()
            .createdAt(newProjectProductTeamHighlight.getCreatedAt())
            .updateAt(newProjectProductTeamHighlight.getUpdatedAt())
            .updateAt(LocalDateTime.now())
            .build();
    }

    /**
     * Creates and stores a new Product Service entry for a given project.
     * <p>
     *     This method verifies the existence of the project, initializes a new
     *     product service entity with the provided vision and mission details,
     *     and saves the entity to the database.
     * </p>
     *
     * @param projectId The unique identifier of the project for which the service details are being created.
     * @param request   The request object containing the vision and mission details of the project.
     * @return A {@link GeneralResponse} containing timestamps of creation and update.
     * @throws ProjectProductNotFoundException if the specified project ID does not exist.
     */
    public GeneralResponse createProductServices(String projectId, ServicesJSONRequest request) {
        /* Check if the project ID currently exist in database */
        ProjectProduct existingProjectProduct = projectProductRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectProductNotFoundException("Invalid project ID"));

        /* Initialize product services instance */
        ProjectProductService newProjectProductService = ProjectProductService.builder()
            .project(existingProjectProduct)
            .vision(request.getVision())
            .mission(request.getMission())
            .build();
        
        /* Save the product servces instance to database */
        newProjectProductService = projectProductServiceRepository.save(newProjectProductService);

        return GeneralResponse.builder()
            .createdAt(newProjectProductService.getCreatedAt())
            .updateAt(newProjectProductService.getUpdatedAt())
            .updateAt(LocalDateTime.now())
            .build();
    }

    /**
     * Creates and stores compliance documents for a given project.
     * <p>
     *     This method verifies the existence of the project, uploads the compliance 
     *     documents to cloud storage, and saves the metadata into the database.
     * </p>
     *
     * @param projectId The unique identifier of the project for which the compliance documents are being uploaded.
     * @param request   The request object containing the compliance documents.
     * @return A {@link GeneralResponse} containing timestamps of creation and update.
     * @throws ProjectProductNotFoundException if the specified project ID does not exist.
     */
    public GeneralResponse createProductComplienceDocuments(String projectId, ComplienceDocumentsFileRequest request) {
        /* Check if the project ID currently exist in database */
        ProjectProduct existingProjectProduct = projectProductRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectProductNotFoundException("Invalid project ID"));
        
        /* Upload file to S3 storage */
        String pathFolder = "projects/overview/complience_documents/" + projectId;

        /* Prepare a list of compliance document entities */
        List<ProjectProductComplienceDocuments> productComplienceDocuments = Stream.of(
            new AbstractMap.SimpleEntry<>("Legal Complience", request.getLegalComplience()),
            new AbstractMap.SimpleEntry<>("Privacy Policy", request.getPrivacyPolicy()),
            new AbstractMap.SimpleEntry<>("Regulatory Approval", request.getRegulatoryApproval()),
            new AbstractMap.SimpleEntry<>("Service Level Agreement", request.getServiceLevelAggrement()),
            new AbstractMap.SimpleEntry<>("Data Processing Agreement", request.getDataProcessingAggremeent()),
            new AbstractMap.SimpleEntry<>("Third Party Compliance", request.getThirdPartyCompliance())
        )
        .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
        .map(entry -> {
            String documentUrl = fileUploader.uploadFile(pathFolder, entry.getValue());
            return ProjectProductComplienceDocuments.builder()
                .project(existingProjectProduct)
                .document(entry.getKey())
                .documentUrl(documentUrl)
                .documentOriginalName(entry.getValue().getOriginalFilename())
                .build();
        })
        .collect(Collectors.toList());

        /* Save all entities to database */
        productComplienceDocuments = projectProductComplienceDocumentsRepository.saveAll(productComplienceDocuments);

        return GeneralResponse.builder()
            .createdAt(LocalDateTime.now())
            .updateAt(LocalDateTime.now())
            .build();
    }
}
