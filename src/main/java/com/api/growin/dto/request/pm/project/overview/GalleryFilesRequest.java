package com.api.growin.dto.request.pm.project.overview;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

public class GalleryFilesRequest {
    /**
     * Validates an multiple uploaded files based on size and type constraints.
     * 
     * @param files    The uploaded file to be validated.
     * @param validator The validation context.
     * @return {@code true} if the file is valid, otherwise {@code false}.
     * @throws {ConstraintViolationException}
     */
    public static void validateFiles(List<MultipartFile> files, Validator validator) {
        for (MultipartFile file : files) {
            GeneralInfoFileRequest fileRequest = new GeneralInfoFileRequest(file);
            Set <ConstraintViolation<GeneralInfoFileRequest>> violations = validator.validate(fileRequest);

            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        }
    }
}
