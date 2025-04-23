package com.api.growin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import lombok.Data;

/**
 * Configuration class for MinIO client setup.
 * <p>
 *     This class defines the configuration for integrating MinIO as an object storage service.
 *     It initializes a {@link MinioClient} bean with the necessary credentials and endpoint
 *     and provides the MinIO bucket name as a bean for dependency injection.
 * </p>
 *
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-03-17
 */
@Configuration
@Data
public class MinioConfig {

    /** The MinIO server endpoint URL. */
    @Value("${minio.url}")
    private String minioEndpoint;

    /** The MinIO access key used for authentication. */
    @Value("${minio.access.key}")
    private String minioAccessKey;

    /** The MinIO secret key used for authentication. */
    @Value("${minio.access.secret}")
    private String minioSecretKey;

    /** The MinIO bucket name used for file storage. */
    @Value("${minio.bucket.name}")
    private String minioBucketName;
    
    /**
     * Creates and configures a {@link MinioClient} bean.
     * <p>
     *     This bean initializes the MinIO client with the configured endpoint and authentication credentials.
     *     The returned instance allows interaction with the MinIO object storage service.
     * </p>
     *
     * @return an instance of {@link MinioClient} configured with the provided credentials
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
            .endpoint(minioEndpoint)
            .credentials(minioAccessKey, minioSecretKey)
            .build();
    }

    /**
     * Provides the MinIO bucket name as a bean.
     * <p>
     *     This bean allows other components to access the configured MinIO bucket name.
     * </p>
     *
     * @return the name of the MinIO bucket
     */
    @Bean
    public String bucketName() { 
        return minioBucketName;
    }
}