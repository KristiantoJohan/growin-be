package com.api.growin.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for product general information addition responses.
 * <p>
 *      This class represents the response returned after a successful product general information addition process.
 *      It contains createdAt and updatedAt
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse {
    /**
     * The Timestamp of product creation.
     */
    private LocalDateTime createdAt;

    /**
     * The Timestamp of product last update.
     */
    private LocalDateTime updateAt;
}
