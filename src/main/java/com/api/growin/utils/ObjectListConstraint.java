package com.api.growin.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.lang.Arrays;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

/**
 * Custom validator for validating a list of uploaded files.
 * <p>
 *     This constraint ensures that each file in the list adheres to rules
 *     regarding file size, MIME type, and presence (if required). Typically used
 *     for validating fields like project galleries or document uploads in form requests.
 * </p>
 *
 * <p>
 *     It supports the following validation rules:
 *     <ul>
 *         <li><b>maxSize</b>: Maximum file size in bytes</li>
 *         <li><b>allowedTypes</b>: Allowed MIME types (e.g., image/jpeg, image/png)</li>
 *         <li><b>required</b>: Whether at least one file must be present</li>
 *     </ul>
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * @ObjectListConstraint.Rules(
 *     maxSize = 5 * 1024 * 1024,
 *     allowedTypes = {"image/png", "image/jpeg"},
 *     required = false
 * )
 * private List<MultipartFile> gallery;
 * }
 * </pre>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
public class ObjectListConstraint implements ConstraintValidator<ObjectListConstraint.Rules, List<MultipartFile>> {

    /** The maximum file size allowed (in bytes). */
    private long maxSize;

    /** The list of allowed MIME types for the uploaded file. */
    private List<String> allowedTypes;

    /** Indicates whether at least one file is required. */
    private boolean required;

    /* Maximum amount of file in a single upload  */
    private int maxCount;

    /**
     * Initializes the validator with the given annotation values.
     *
     * @param constraintAnnotation The constraint annotation providing the validation rules.
     */
    @Override
    public void initialize(ObjectListConstraint.Rules constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = Arrays.asList(constraintAnnotation.allowedTypes());
        this.required = constraintAnnotation.required();
        this.maxCount = constraintAnnotation.value();
    }

    /**
     * Validates the list of MultipartFile objects based on the specified rules.
     *
     * @param files   The list of files to validate.
     * @param context The constraint validator context.
     * @return {@code true} if the file list is valid, otherwise {@code false}.
     */
    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if ((files == null || files.isEmpty())) {
            if (required) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("At least one file is required")
                       .addConstraintViolation();
                return false;
            }

            return true;
        } else if (files.size() > maxCount) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Maximum file is " + maxCount)
                    .addConstraintViolation();
            return false;
        }


        for (MultipartFile file : files) {
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
                context.buildConstraintViolationWithTemplate("File type is not allowed! Allowed: " + String.join(", ", allowedTypes))
                       .addConstraintViolation();
                return false;
            }
        }

        return true;
    }

    /**
     * Annotation to declare validation rules for a list of files.
     * <p>
     *     Applied on fields (usually `List<MultipartFile>`) to enforce constraints
     *     such as maximum file size, allowed MIME types, and whether the field is required.
     * </p>
     */
    @Documented
    @Constraint(validatedBy = ObjectListConstraint.class)
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Rules {

        /**
         * Default validation message used when no specific error is set.
         */
        String message() default "Invalid file(s)";

        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};

        /**
         * The maximum size allowed for each file, in bytes.
         */
        long maxSize() default 5 * 1024 * 1024;

        /**
         * The allowed MIME types for each file.
         */
        String[] allowedTypes() default { "image/jpeg", "image/png", "application/pdf" };

        /**
         * Whether the list must contain at least one file.
         */
        boolean required() default true;

        int value() default 5;
    }
}