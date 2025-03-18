package com.api.growin.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.api.growin.exceptions.MinioRuntimeException;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;

/**
 * Utility class for handling file uploads, deletions, and updates using MinIO.
 * <p>
 *     This class provides methods to upload, retrieve, and delete files from a MinIO storage.
 *     It uses the {@link OperationExecutor} to handle execution and exception management.
 * </p>
 * 
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-02-14
 */
@Component
@RequiredArgsConstructor
public class FileUploader {

    /** The name of the bucket used for file storage. */
    private final String bucketName;

    /** The MinIO client instance for interacting with the storage. */
    private final MinioClient minioClient;

    /**
     * Uploads a single file to MinIO storage and returns its pre-signed URL.
     * <p>
     *     If the specified bucket does not exist, it will be created before uploading the file.
     *     The uploaded file is assigned a unique name using {@link UUID}.
     * </p>
     *
     * @param file the file to be uploaded
     * @return the pre-signed URL of the uploaded file
     * @throws MinioRuntimeException if the upload operation fails
     */
    public String uploadFile(MultipartFile file) {
        return OperationExecutor.execute(() -> {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                    PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(inputStream, file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
                );
            }

            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileName)
                    .build()
            );
        }, "MinIO upload failed", MinioRuntimeException.class);
    }

    /**
     * Uploads multiple files to MinIO storage.
     * <p>
     *     Each file is uploaded individually using {@link #uploadFile(MultipartFile)}.
     * </p>
     *
     * @param files a list of files to be uploaded
     * @return a list of pre-signed URLs of the uploaded files
     * @throws MinioRuntimeException if any upload operation fails
     */
    public List<String> uploadFiles(List<MultipartFile> files) {
        /* Array to store the new URLs of uploaded files */
        List<String> fileUrls = new ArrayList<>();

        /* Upload all files */
        for (MultipartFile file : files) {
            fileUrls.add(uploadFile(file));
        }

        return fileUrls;
    }

    /**
     * Deletes a file from MinIO storage.
     * <p>
     *     This method removes the specified file from the bucket.
     * </p>
     *
     * @param fileName the name of the file to be deleted
     * @throws MinioRuntimeException if the delete operation fails
     */
    public void deleteFile(String fileName) {
        OperationExecutor.execute(() -> {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build()
            );

            return null;
        }, "MinIO delete failed", MinioRuntimeException.class);
    }

    /**
     * Updates an existing file in MinIO storage.
     * <p>
     *     The old file is deleted before the new file is uploaded.
     * </p>
     *
     * @param oldFileName the name of the file to be replaced
     * @param newFile     the new file to be uploaded
     * @return the pre-signed URL of the newly uploaded file
     * @throws MinioRuntimeException if the update operation fails
     */
    public String updateFile(String oldFileName, MultipartFile newFile) {
        deleteFile(oldFileName);
        return uploadFile(newFile);
    }
}
