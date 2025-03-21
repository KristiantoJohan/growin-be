package com.api.growin.controllers.pm.projects;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.growin.dto.request.pm.project.overview.ComplienceDocumentsFileRequest;
import com.api.growin.dto.request.pm.project.overview.GalleryFilesRequest;
import com.api.growin.dto.request.pm.project.overview.GeneralInfoFileRequest;
import com.api.growin.dto.request.pm.project.overview.GeneralInfoJSONRequest;
import com.api.growin.dto.request.pm.project.overview.ServicesJSONRequest;
import com.api.growin.dto.request.pm.project.overview.TeamHighlightJSONRequest;
import com.api.growin.dto.response.GeneralResponse;
import com.api.growin.dto.response.pm.project.ProjectInitResponse;
import com.api.growin.models.User;
import com.api.growin.services.pm.projects.OverviewService;
import com.api.growin.utils.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * REST Controller for managing project-related operations.
 * <p>
 *     This controller provides API endpoints for initializing projects and 
 *     handling project-related information such as general information.
 *     It acts as the entry point for project management in the system.
 * </p>
 *
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-03-17
 */
@RestController
@RequestMapping("api/v1/pm/project")
@RequiredArgsConstructor
public class OverviewController {

    /** Service for handling project-related business logic. */
    @Autowired
    private final OverviewService overviewService;

    @Autowired
    private Validator validator;

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
    public ResponseEntity<Object> initializeProject(@AuthenticationPrincipal User user) {
        /* Initialize new project with userId */
        ProjectInitResponse response = overviewService.projectInit(user.getId().toString());

        /* Sending response */
        return HttpResponse.successResponse("Successfully initialize new project", response);
    }

     /**
     * Adds general information for a project.
     * <p>
     *     This endpoint accepts project general information in JSON format along 
     *     with an uploaded file, processes them, and stores the project data.
     * </p>
     *
     * @param data The JSON data containing project details.
     * @param file The uploaded file associated with the project.
     * @return A response entity indicating the success of the operation.
     * @throws JsonProcessingException If there is an error processing the JSON data.
     */
    @PostMapping(value = "/{projectid}/overview/general-information/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createProductGeneralInformation(@PathVariable UUID projectid, @RequestPart("json") @Valid String data, @Valid @ModelAttribute GeneralInfoFileRequest file) throws JsonProcessingException {
        /* Convert JSON string to Java Object and File to MultipartFile */
        ObjectMapper objectMapper = new ObjectMapper();
        GeneralInfoJSONRequest request = objectMapper.readValue(data, GeneralInfoJSONRequest.class);
        MultipartFile requestFile = file.getFile();

        /* Call the service method */
        GeneralResponse response = overviewService.createProductGeneralInformation(projectid.toString(), request, requestFile);

        /* Sending response */
        return HttpResponse.successResponse("Successfully adding new general information", response);
    }
    
    /**
     * Uploads product gallery images for a specific project.
     * <p>
     *     This endpoint allows users to upload multiple images for the 
     *     product gallery of a given project. The images are validated 
     *     before being processed and stored.
     * </p>
     *
     * @param projectid The unique identifier of the project.
     * @param files A list of images to be uploaded.
     * @return A response entity indicating the success of the operation.
     */
    @PostMapping(value = "/{projectid}/overview/product-gallery/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createProductGallery(@PathVariable UUID projectid, @RequestPart("photos") List<MultipartFile> files) {
        /* Validating uploaded files */
        GalleryFilesRequest.validateFiles(files, validator);

        /* Call the service method */
        GeneralResponse response = overviewService.createProductGallery(projectid.toString(), files);

        /* Sending response */
        return HttpResponse.successResponse("Successfully adding new product gallery", response);
    }

    /**
     * Handles HTTP POST requests to create a new Product Team Highlight for a specific project.
     * <p>
     *     This endpoint receives a JSON request containing team highlight details and
     *     stores the information in the database for the specified project.
     * </p>
     *
     * @param projectid The unique identifier of the project for which the team highlight is being created.
     * @param request   The validated request body containing team composition and leadership details.
     * @return A {@link ResponseEntity} containing a success message and a {@link GeneralResponse} object with timestamps.
     */
    @PostMapping(value = "/{projectid}/overview/product-team-highlight/")
    public ResponseEntity<Object> createProductTeamHighlight(@PathVariable UUID projectid, @Valid @RequestBody TeamHighlightJSONRequest request) {
        /* Call the service method */
        GeneralResponse response = overviewService.createProductTeamHighlight(projectid.toString(), request);

        /* Sending response */
        return HttpResponse.successResponse("Successfully adding new product team highlight", response);
    }

    /**
     * Handles the HTTP request for creating and storing a new Product Service entry for a given project.
     * <p>
     *     This endpoint allows clients to submit vision and mission details for a specific project.
     *     It validates the request, invokes the service layer to process and store the information,
     *     and returns a standardized HTTP response.
     * </p>
     *
     * @param projectid The unique identifier of the project for which the service details are being created.
     * @param request   The request object containing the vision and mission details of the project.
     * @return A {@link ResponseEntity} containing a success message and the service creation details.
     */
    @PostMapping(value = "/{projectid}/overview/product-services/")
    public ResponseEntity<Object> createProductServices(@PathVariable UUID projectid, @Valid @RequestBody ServicesJSONRequest request) {
        /* Call the service method */
        GeneralResponse response = overviewService.createProductServices(projectid.toString(), request);

        /* Sending response */
        return HttpResponse.successResponse("Successfully adding new product services", response);
    }

    @PostMapping(value = "/{projectid}/overview/product-complience-documents/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createProductComplienceDocuments(@PathVariable UUID projectid, @Valid @ModelAttribute ComplienceDocumentsFileRequest request) {
        /* Call the service method */
        GeneralResponse response = overviewService.createProductComplienceDocuments(projectid.toString(), request);
        
        /* Sending response */
        return HttpResponse.successResponse("Successfully adding new product complience documents", response);
    }
    
}