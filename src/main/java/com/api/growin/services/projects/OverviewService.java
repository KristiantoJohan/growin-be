package com.api.growin.services.projects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.api.growin.dto.request.projects.ProjectComplienceDocumentsRequest;
import com.api.growin.dto.request.projects.ProjectGalleryRequest;
import com.api.growin.dto.request.projects.ProjectOverviewRequest;
import com.api.growin.dto.response.GeneralResponse;
import com.api.growin.exceptions.ProjectNotFoundException;
import com.api.growin.models.Category;
import com.api.growin.models.CurrentStage;
import com.api.growin.models.Platform;
import com.api.growin.models.Project;
import com.api.growin.models.ProjectComplienceDocuments;
import com.api.growin.models.ProjectGallery;
import com.api.growin.models.ProjectStepOverview;
import com.api.growin.repositories.ProjectComplienceDocumentsRepository;
import com.api.growin.repositories.ProjectGalleryRepository;
import com.api.growin.repositories.ProjectRepository;
import com.api.growin.repositories.ProjectStepOverviewRepository;
import com.api.growin.utils.FileUploader;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Service class for handling project overview operations.
 * <p>
 *     This service manages the business logic related to updating a project's
 *     overview data, including general information, category, platform, stage, 
 *     and compliance-related documents.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Service
@RequiredArgsConstructor
public class OverviewService {
    /**
     *  Initialize dependencies
     */
    private final ProjectStepOverviewRepository projectStepOverviewRepository;
    private final ProjectRepository projectRepository;
    private final ProjectComplienceDocumentsRepository projectComplienceDocumentsRepository;
    private final ProjectGalleryRepository projectGalleryRepository;

    /* Utils */
    private final FileUploader fileUploader;

    /**
     * Updates the overview details of a specified project.
     * <p>
     *     This method updates a project's overview information with the provided data.
     *     It includes patching non-null fields, handling enum values, and uploading 
     *     file to an external file storage (e.g., AWS S3).
     *     If the project or its overview section is not found, an exception is thrown.
     * </p>
     *
     * @param projectId The unique identifier of the project to be updated.
     * @param request   The payload containing the overview update data.
     * @return A {@link GeneralResponse} containing timestamps of the updated project overview.
     * @throws ProjectNotFoundException if the project with the given ID does not exist.
     * @throws EntityNotFoundException if the project overview section is not found.
     */
    @Transactional
    public GeneralResponse updateProjectOverview(String projectId, ProjectOverviewRequest request) {
        /* Check if the project ID existed */
        Project project = projectRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectNotFoundException("Selected project not found"));

        /* Path folder for S3 */
        String pathFolder = "projects/overview/" + project.getId() + "/logo";

        /* Get the entity of project overview */
        ProjectStepOverview projectStepOverview = projectStepOverviewRepository.findByProject(project);
        if (projectStepOverview == null) {
            throw new EntityNotFoundException("Project overview not found");
        }

        /**
         *  Update product logo if existed and upload if not existed
         */
        String logoPath = projectStepOverview.getLogo();
        if (request.getLogo() != null && !request.getLogo().isEmpty()) {
            if (logoPath == null || logoPath.isEmpty()) {
                logoPath = fileUploader.uploadFile(pathFolder, request.getLogo());
            } else {
                logoPath = fileUploader.updateFile(logoPath, pathFolder, request.getLogo());
            }
            projectStepOverview.setLogo(logoPath);
        }        

        /**
         *  Update the selected product overview
         */
        projectStepOverview.setLogo(logoPath);
        projectStepOverview.setProductName(request.getProductName().toString());
        projectStepOverview.setTagline(request.getTagline().toString());
        projectStepOverview.setDescription(request.getDescription().toString());
        projectStepOverview.setCategory(Category.fromString(request.getCategory().toString()));
        projectStepOverview.setStage(CurrentStage.fromString(request.getStage().toString()));
        projectStepOverview.setPlatform(Platform.fromString(request.getPlatform().toString()));
        projectStepOverview.setWebsiteUrl(request.getWebsiteUrl().toString());
        projectStepOverview.setTeamInCharge(request.getTeamInCharge().toString());
        projectStepOverview.setHustler(request.getHustler());
        projectStepOverview.setHipster(request.getHipster());
        projectStepOverview.setHacker(request.getHacker());
        projectStepOverview.setEmail(request.getEmail().toString());
        projectStepOverview.setPhone(request.getPhone().toString());
        projectStepOverview.setProductVision(request.getProductVision().toString());
        projectStepOverview.setProductMission(request.getProductMission().toString());

        projectStepOverview = projectStepOverviewRepository.save(projectStepOverview);

        return GeneralResponse.builder()
            .createdAt(projectStepOverview.getCreatedAt())
            .updateAt(projectStepOverview.getUpdatedAt())
            .build();
    }

    /**
     * Handles the upload of compliance documents for a specific project.
     * <p>
     *     This method checks if the project exists in the database, deletes any previous compliance documents associated with the project, 
     *     and uploads new compliance documents to S3 storage. It then updates the database with the new document URLs and information.
     * </p>
     * 
     * @param projectId The unique identifier of the project for which compliance documents are being uploaded.
     * @param request Contains the compliance documents to be uploaded.
     * @return A {@link GeneralResponse} indicating the result of the operation, including timestamps for creation and update.
     * @throws ProjectNotFoundException If the project with the specified ID cannot be found in the database.
     */
    @Transactional
    public GeneralResponse uploadComplienceDocuments(String projectId, ProjectComplienceDocumentsRequest request) {
        /* Check if the project ID currently exist in database */
        Project project = projectRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectNotFoundException("Invalid project ID"));
        
        /* Upload file to S3 storage */
        String pathFolder = "projects/overview/" + project.getId() + "/complience_documents";

        /* Get and delete all previous documents */
        List<ProjectComplienceDocuments> existingDocs = projectComplienceDocumentsRepository.findByProject(project);

        Map<String, MultipartFile> incomingDocs = new HashMap<>();
        if (request.getLegalComplience() != null) incomingDocs.put("Legal Complience", request.getLegalComplience());
        if (request.getPrivacyPolicy() != null) incomingDocs.put("Privacy Policy", request.getPrivacyPolicy());
        if (request.getRegulatoryApproval() != null) incomingDocs.put("Regulatory Approval", request.getRegulatoryApproval());
        if (request.getServiceLevelAgreement() != null) incomingDocs.put("Service Level Agreement", request.getServiceLevelAgreement());
        if (request.getDataProcessingAgreement() != null) incomingDocs.put("Data Processing Agreement", request.getDataProcessingAgreement());
        if (request.getThirdPartyComplience() != null) incomingDocs.put("Third Party Compliance", request.getThirdPartyComplience());

        // Prepare list dokumen yang akan disimpan ke DB
        List<ProjectComplienceDocuments> updatedDocs = new ArrayList<>();

        for (Map.Entry<String, MultipartFile> entry : incomingDocs.entrySet()) {
            String documentName = entry.getKey();
            MultipartFile file = entry.getValue();
    
            if (file != null && !file.isEmpty()) {
                // Cek apakah dokumen lama sudah ada
                Optional<ProjectComplienceDocuments> existingDocOpt = existingDocs.stream()
                    .filter(doc -> doc.getDocument().equals(documentName))
                    .findFirst();
    
                String newUrl;
                if (existingDocOpt.isPresent()) {
                    // Hapus file lama di MinIO
                    ProjectComplienceDocuments existingDoc = existingDocOpt.get();
                    fileUploader.deleteFile(existingDoc.getDocumentUrl());
                }
    
                // Upload file baru
                newUrl = fileUploader.uploadFile(pathFolder, file);
    
                // Build entity baru atau update lama
                ProjectComplienceDocuments newDoc = ProjectComplienceDocuments.builder()
                    .project(project)
                    .document(documentName)
                    .documentUrl(newUrl)
                    .documentOriginalName(file.getOriginalFilename())
                    .build();
    
                updatedDocs.add(newDoc);
            }
        }
    
        /* Delete the previous url and then perform file upload */
        if (!updatedDocs.isEmpty()) {
            // delete the previous one with the corresponding document name
            List<String> updatedDocNames = updatedDocs.stream().map(ProjectComplienceDocuments::getDocument).toList();
            projectComplienceDocumentsRepository.deleteByProjectAndDocumentIn(project, updatedDocNames);
    
            /* Save the new one */
            projectComplienceDocumentsRepository.saveAll(updatedDocs);
        }

        return GeneralResponse.builder()
            .createdAt(LocalDateTime.now())
            .updateAt(LocalDateTime.now())
            .build();
    }

    /**
     * Handles the upload of product gallery images for a specific project.
     * <p>
     *     This method verifies if the project exists in the database, uploads the gallery images to an S3 storage,
     *     and saves the URLs of the gallery images in the database. It supports uploading multiple images for the project.
     * </p>
     * 
     * @param projectId The unique identifier of the project for which the gallery images are being uploaded.
     * @param request Contains a list of gallery images to be uploaded.
     * @return A {@link GeneralResponse} indicating the result of the operation, including timestamps for creation and update.
     * @throws ProjectNotFoundException If the project with the specified ID cannot be found in the database.
     */
    @Transactional
    public GeneralResponse uploadProductGallery(String projectId, ProjectGalleryRequest request) {
        /* Check if the project ID currently exist in database */
        Project project = projectRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectNotFoundException("Invalid project ID"));

        /* Upload file to S3 storage */
        String pathFolder = "projects/overview/" + project.getId() + "/gallery";
        List<MultipartFile> photos = request.getGallery();
        List<String> uploadedPhotoUrl = fileUploader.uploadFiles(pathFolder, photos);
        
        /* List for all new entities */
        List <ProjectGallery> projectProductGalleries = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            /* Initialize instances for each uploaded file */
            projectProductGalleries.add(
                ProjectGallery.builder()
                    .project(project)
                    .url(uploadedPhotoUrl.get(i))
                    .build()
            );
        }

        /* Save all entities to database */
        projectProductGalleries = projectGalleryRepository.saveAll(projectProductGalleries);

        return GeneralResponse.builder()
            .createdAt(LocalDateTime.now())
            .updateAt(LocalDateTime.now())
            .build();
    }
    
    /**
     * Retrieves and serves a file (image or document) from the storage by its path.
     *
     * @param path The relative path of the file to retrieve.
     * @return A {@link ResponseEntity} wrapping the requested resource.
     */
    public ResponseEntity<Resource> serveFile(String path) {
        return fileUploader.getFileAsResponse(path);
    }
}