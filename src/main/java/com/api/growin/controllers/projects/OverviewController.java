package com.api.growin.controllers.projects;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.growin.dto.request.projects.ProjectComplienceDocumentsRequest;
import com.api.growin.dto.request.projects.ProjectGalleryRequest;
import com.api.growin.dto.request.projects.ProjectOverviewRequest;
import com.api.growin.dto.response.GeneralResponse;
import com.api.growin.services.projects.OverviewService;
import com.api.growin.utils.HttpResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class responsible for managing product/project overview-related operations.
 * <p>
 * 		Provides endpoints for updating the project overview, uploading compliance documents, uploading gallery images,
 * 		and retrieving files from storage.
 * </p>
 * 
 * <p>	Base URL: <b>/api/v1/pm/project/overview</b></p>
 * 
 * @author Johan Kristianto
 */
@RestController
@RequestMapping("api/v1/pm/project/overview")
@RequiredArgsConstructor
public class OverviewController {

    /* Initialize dependencies */
    @Autowired private final OverviewService overviewService;
    
    /**
     * Updates the overview of a given project.
     *
     * @param projectId The UUID of the project to update.
     * @param request The overview update request data wrapped in {@link ProjectOverviewRequest}.
     * @return A {@link ResponseEntity} with a success message and the update result.
     */
    @PatchMapping(path = "/{projectId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateProjectOverview(@PathVariable UUID projectId, @Valid @ModelAttribute ProjectOverviewRequest request) {

        /* Update the selected project overview */
        GeneralResponse updateProjectOverview = overviewService.updateProjectOverview(projectId.toString(), request);
        return HttpResponse.successResponse("Successfully updating overview", updateProjectOverview);
    }
    
    /**
     * Uploads or updates compliance documents for the specified project.
     *
     * @param projectId The UUID of the project for which documents are uploaded.
     * @param request A request wrapper that contains multiple compliance document files.
     * @return A {@link ResponseEntity} with the operation result.
     */
    @PostMapping(path = "/compliencedocs/{projectId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadProjectComplienceDocuments(@PathVariable UUID projectId, @Valid @ModelAttribute ProjectComplienceDocumentsRequest request) {

        /* Upload the complience documents of the selected project */
        GeneralResponse uploadProjectComplienceDocuments = overviewService.uploadComplienceDocuments(projectId.toString(), request);
        return HttpResponse.successResponse("Successfully updating overview", uploadProjectComplienceDocuments);
    }
    
    /**
     * Uploads a gallery of images for the specified project.
     *
     * @param projectId The UUID of the project whose gallery is being updated.
     * @param request A request that contains multiple gallery image files.
     * @return A {@link ResponseEntity} with the operation result.
     */
    @PostMapping(path = "/gallery/{projectId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadProjectGallery(@PathVariable UUID projectId, @Valid @ModelAttribute ProjectGalleryRequest request) {
        
        /* Upload the gallery of the selected project */
        GeneralResponse uploadProjectComplienceDocuments = overviewService.uploadProductGallery(projectId.toString(), request);
        return HttpResponse.successResponse("Successfully updating overview", uploadProjectComplienceDocuments);
    }
    
    /**
     * Retrieves and serves a file (image or document) from the storage by its path.
     *
     * @param path The relative path of the file to retrieve.
     * @return A {@link ResponseEntity} wrapping the requested resource.
     */
    @GetMapping("/files/view")
    public ResponseEntity<Resource> getFile(@RequestParam String path) {
        return overviewService.serveFile(path);
    }    
}