package com.api.growin.dto.request.projects;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.api.growin.utils.ObjectListConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload for uploading project gallery images.
 * <p>
 *     This DTO is used to handle the upload of multiple gallery images for a project.
 *     Each image must conform to the specified constraints, including file type and size.
 * </p>
 *
 * <p>
 *     The {@code @ObjectListConstraint.Rules} annotation validates that each uploaded file
 *     is of an allowed MIME type (JPG or PNG) and does not exceed the maximum size limit (5MB).
 * </p>
 *
 * <p>
 *     This class uses Lombok annotations to automatically generate constructors, getters,
 *     setters, and the builder pattern.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGalleryRequest {

    /**
     * A list of gallery images to be uploaded for the project.
     * <p>
     *     Each file must be a JPEG or PNG image and must not exceed 5MB in size.
     * </p>
     */
    @ObjectListConstraint.Rules(required = false, allowedTypes = { "image/jpeg", "image/png" }, message = "Each gallery photo must be JPG/PNG and max 5mb")
    private List<MultipartFile> gallery;
}