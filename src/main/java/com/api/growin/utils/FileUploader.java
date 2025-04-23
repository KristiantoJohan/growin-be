package com.api.growin.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.api.growin.exceptions.MinioRuntimeException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
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
     * @param folder the target location for the uploaded file
     * @param file the file to be uploaded
     * @return the pre-signed URL of the uploaded file
     * @throws MinioRuntimeException if the upload operation fails
     */
    public String uploadFile(String folder, MultipartFile file) {
        return OperationExecutor.execute(() -> {
            String fileName = folder + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

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

            return fileName;

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
    public List<String> uploadFiles(String folder, List<MultipartFile> files) {
        /* Array to store the new URLs of uploaded files */
        List<String> fileUrls = new ArrayList<>();

        /* Upload all files */
        for (MultipartFile file : files) {
            fileUrls.add(uploadFile(folder, file));
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
    public String updateFile(String oldFileName, String folder, MultipartFile newFile) {
        deleteFile(oldFileName);
        return uploadFile(folder, newFile);
    }

    /**
     * Upload and return the url of a file in MinIO storage
     * <p>
     *     Return the path url of the uploaded file
     * </p>
     * 
     * @param file the uploaded file
     * @param path Path folder in S3 server
     * @return the url of the uploaded file
     */
    public String uploadIfPresent(MultipartFile file, String path) {
        if (file != null && !file.isEmpty()) {
            return uploadFile(path, file);
        }

        return null;
    }

    /**
     * Upload and return the url of a file in MinIO storage
     * <p>
     *     Return the path url of the uploaded file
     * </p>
     * 
     * @param file the uploaded file
     * @param path Path folder in S3 server
     * @return the url of the uploaded file
     */
    public String updateIfPresent(MultipartFile file, String oldPath, String newPath) {
        if (file != null && !file.isEmpty()) {
            if (oldPath.equals("")) {
                return uploadFile(newPath, file);
            }
            return updateFile(oldPath, newPath, file);
        }

        return null;
    }
    
    public ResponseEntity<Resource> getFileAsResponse(String objectPath) {
        return OperationExecutor.execute(() -> {
            /* Getting the information from minio */
            GetObjectResponse objectResponse = minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectPath)
                    .build()
            );

            /* Getting the metadata */
            StatObjectResponse statObjectResponse = minioClient.statObject(
                StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectPath)
                    .build()
            );

            // Buat nama file dari akhir path
            String[] pathParts = objectPath.split("/");
            String filename = pathParts[pathParts.length - 1];

            // Bungkus input stream sebagai Resource
            InputStreamResource resource = new InputStreamResource(objectResponse);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(statObjectResponse.contentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);


        }, "Error while getting the selected object", MinioRuntimeException.class);
    }
}