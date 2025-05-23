package com.api.growin.services.projects;

import com.api.growin.dto.request.projects.ProjectMasterDataRequest;
import com.api.growin.dto.request.projects.ProjectMasterPackageRequest;
import com.api.growin.dto.request.projects.ProjectPricingPlanRequest;
import com.api.growin.dto.response.GeneralResponse;
import com.api.growin.exceptions.ProjectNotFoundException;
import com.api.growin.models.Grade;
import com.api.growin.models.MasterData;
import com.api.growin.models.MasterPackage;
import com.api.growin.models.PackageLevel;
import com.api.growin.models.PricingStatus;
import com.api.growin.models.Project;
import com.api.growin.models.ProjectPricingPlan;
import com.api.growin.repositories.ProjectMasterDataRepository;
import com.api.growin.repositories.ProjectMasterPackageRepository;
import com.api.growin.repositories.ProjectPricingPlanRepository;
import com.api.growin.repositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for managing pricing plan updates for a given project.
 * <p>
 *     This service handles the business logic required to retrieve and update
 *     pricing plan data associated with a project. It ensures data consistency
 *     by validating project existence and applying transactional integrity.
 * </p>
 *
 * <p>
 *     The class integrates with Spring's dependency injection mechanism via Lombok's
 *     {@code @RequiredArgsConstructor} and uses {@code @Transactional} to manage
 *     database operations atomically.
 * </p>
 *
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-05-13
 */
@Service
@RequiredArgsConstructor
public class PricingPlanService {
		 /*
				Initialize dependencies
			*/
		 private final ProjectPricingPlanRepository projectPricingPlanRepository;
		 private final ProjectMasterPackageRepository projectMasterPackageRepository;
		 private final ProjectMasterDataRepository projectMasterDataRepository;
		 private final ProjectRepository projectRepository;

			/**
			 * Marks the pricing plan process of a project as requested.
			 * <p>
			 *     This method is used to update the pricing status of a specific project
			 *     to {@code REQUESTED}, indicating that a pricing plan has been formally initiated.
			 *     It performs validation to ensure the project exists before applying the update.
			 * </p>
			 *
			 * <p>
			 *     The method persists the status change to the database and returns a general response
			 *     containing timestamps for auditing purposes.
			 * </p>
			 *
			 * @param projectId The UUID string of the project for which the pricing plan is being requested.
			 * @return A {@link GeneralResponse} containing the creation and update timestamps of the project.
			 * @throws ProjectNotFoundException if no project exists with the provided ID.
			 */
		 public GeneralResponse requestPricingPlan(String projectId) {
				/* Check if the project ID existed */
				Project project = projectRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectNotFoundException("Selected project not found"));

				/* Set the selected project pricing status to complete */
				project.setPricingStatus(PricingStatus.REQUESTED);
				project = projectRepository.save(project);

				return GeneralResponse.builder()
						 .createdAt(project.getCreatedAt())
						 .updateAt(project.getUpdatedAt())
						 .build();
		 }

			/**
			 * Updates the pricing plan associated with a specific project.
			 *
			 * <p>
			 *     This method first checks whether the specified project exists. If found,
			 *     it retrieves the related pricing plan entity and updates it with the new
			 *     target strategy and benchmark prices provided in the request.
			 * </p>
			 *
			 * @param projectId The UUID string of the project to be updated.
			 * @param request   The payload containing new pricing strategy and benchmark data.
			 * @return A {@link GeneralResponse} containing metadata such as creation and update timestamps.
			 * @throws ProjectNotFoundException   if the project with the given ID does not exist.
			 * @throws EntityNotFoundException    if the associated pricing plan record is not found.
			 */
		 @Transactional
		 public GeneralResponse updateProjectPricingPlan(String projectId, ProjectPricingPlanRequest request) {
					/* Check if the project ID existed */
					Project project = projectRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectNotFoundException("Selected project not found"));

					/* Get the entity of the selected project pricing plan */
					ProjectPricingPlan projectPricingPlan = projectPricingPlanRepository.findByProject(project);
					if (projectPricingPlan == null) {
							throw new EntityNotFoundException("Project pricing plan not found");
					}

					/* Update the selected project pricing plan */
					projectPricingPlan.setTargetPriceStrategy(request.getTargetPriceStrategy());
					projectPricingPlan.setTargetPriceBenchmark(request.getTargetPriceBenchmark());

					/* Save the new one */
					projectPricingPlan = projectPricingPlanRepository.save(projectPricingPlan);

					/* Set the selected project pricing status to complete */
					project.setPricingStatus(PricingStatus.COMPLETE);

					project = projectRepository.save(project);

					return GeneralResponse.builder()
							.createdAt(projectPricingPlan.getCreatedAt())
							.updateAt(projectPricingPlan.getUpdatedAt())
							.build();
		 }

		 public GeneralResponse createMasterData(ProjectMasterDataRequest request) {
					MasterData newMasterData = MasterData.builder()
						 .name(request.getName())
						 .grade(Grade.fromString(request.getGrade()))
						 .brand(request.getBrand())
						 .price(request.getPrice())
						 .build();

					newMasterData = projectMasterDataRepository.save(newMasterData);

					return GeneralResponse.builder()
						 .createdAt(newMasterData.getCreatedAt())
						 .updateAt(newMasterData.getUpdatedAt())
						 .build();
		 }

	 public GeneralResponse createMasterPackage(ProjectMasterPackageRequest request) {
			MasterPackage newMasterPackage = MasterPackage.builder()
							.name(request.getName())
							.packageLevel(PackageLevel.fromString(request.getPackageLevel()))
							.price(request.getPrice())
							.build();

			newMasterPackage = projectMasterPackageRepository.save(newMasterPackage);

			return GeneralResponse.builder()
							.createdAt(newMasterPackage.getCreatedAt())
							.updateAt(newMasterPackage.getUpdatedAt())
							.build();
	 }
}