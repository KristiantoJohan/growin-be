package com.api.growin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point of the ProfitPlus application.
 *
 * <p>
 * 	    This class is responsible for bootstrapping and launching the Spring Boot application.
 * 	    It leverages {@link SpringBootApplication} annotation to enable auto-configuration,
 * 	    component scanning, and configuration properties.
 * </p>
 *
 * <p>
 * 	    To start the application, the {@code main} method invokes {@link SpringApplication#run(Class, String...)}
 * 	    which initializes the Spring Boot application context and starts the embedded web server.
 * </p>
 */
@SpringBootApplication
public class GrowinApplication {

    /**
     * The main method that serves as the entry point for the Spring Boot application.
     *
     * 	@param args Command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(GrowinApplication.class, args);
    }
}
