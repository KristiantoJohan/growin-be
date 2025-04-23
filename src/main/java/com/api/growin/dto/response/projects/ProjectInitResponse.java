package com.api.growin.dto.response.projects;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for product first initialization responses.
 * <p>
 *      This class represents the response returned after a successful product initialization process.
 *      It contains product ID
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInitResponse {
    /**
     * The unique identifier of the initialized product.
     */
    private String id;

    /**
     * Identify the last editor of the project
     */
    private String user_id;

    /**
     * The Timestamp of product creation.
     */
    private LocalDateTime createdAt;

    /**
     * The Timestamp of product last update.
     */
    private LocalDateTime updateAt;
}