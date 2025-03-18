package com.api.growin.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

/**
 * Validator class for image file uploads.
 * <p>
 *     This class implements a custom validation for uploaded image files, ensuring
 *     that they meet specified size constraints and allowed MIME types. It is
 *     associated with the {@link ValidImage} annotation.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
public class ObjectConstraint implements ConstraintValidator<ObjectConstraint.Rules, MultipartFile> {
    
    /** The maximum file size allowed (in bytes). */
    private long maxSize;
    
    /** The list of allowed MIME types for the uploaded file. */
    private List<String> allowedTypes;

    /**
     * Initializes the validator with parameters defined in the {@link ValidImage} annotation.
     * 
     * @param constraintAnnotation The annotation instance containing configuration values.
     */
    @Override
    public void initialize(Rules constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = Arrays.asList(constraintAnnotation.allowedTypes());
    }

    /**
     * Validates an uploaded file based on size and type constraints.
     * 
     * @param file    The uploaded file to be validated.
     * @param context The validation context.
     * @return {@code true} if the file is valid, otherwise {@code false}.
     */
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File must not be empty")
                   .addConstraintViolation();
            return false;
        }

        if (file.getSize() > maxSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File is too big. Max: " + (maxSize / 1024 / 1024) + "MB.")
                   .addConstraintViolation();
            return false;
        }
        
        if (!allowedTypes.contains(file.getContentType())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File type is not allowed! Allowed types: " + String.join(", ", allowedTypes))
                   .addConstraintViolation();
            return false;
        }

        return true;
    }

    /**
     * Custom annotation for validating image uploads.
     * <p>
     *     This annotation can be applied to file upload fields to enforce
     *     constraints on file size and allowed MIME types.
     * </p>
     */
    @Documented
    @Constraint(validatedBy = ObjectConstraint.class)
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Rules {
        
        /** The default validation message. */
        String message() default "Invalid file";
        
        /** Groups used for validation. */
        Class<?>[] groups() default {};
        
        /** Payload type for carrying additional information. */
        Class<? extends Payload>[] payload() default {};
        
        /** The maximum allowed file size (default: 5MB). */
        long maxSize() default 5 * 1024 * 1024; 
        
        /** The allowed MIME types for file uploads (default: JPEG, PNG, PDF). */
        String[] allowedTypes() default { "image/jpeg", "image/png", "application/pdf" };
    }
}
