package com.api.growin.dto.response.projects;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO representing a gallery item associated with a project.
 * <p>
 *     This class is used to return details of a specific image file
 *     that is part of the project’s gallery, including its metadata
 *     and creation/update timestamps.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectGalleryResponse {

    /**
     * The unique identifier of the gallery image.
     */
    private String id;

    /**
     * The URL or path to the gallery image resource.
     */
    private String url;

    /**
     * The timestamp indicating when the image was uploaded or added to the gallery.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp indicating the last update to the gallery image record.
     */
    private LocalDateTime updateAt;
}