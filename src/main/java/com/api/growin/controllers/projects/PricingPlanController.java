package com.api.growin.controllers.projects;

import com.api.growin.dto.request.projects.ProjectMasterDataRequest;
import com.api.growin.dto.request.projects.ProjectMasterPackageRequest;
import com.api.growin.dto.request.projects.ProjectPricingPlanRequest;
import com.api.growin.dto.response.GeneralResponse;
import com.api.growin.services.projects.PricingPlanService;
import com.api.growin.utils.HttpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for handling project pricing plan-related operations.
 * <p>
 *     This controller exposes endpoints to:
 *     <ul>
 *         <li> Request a pricing plan for a specific project (used by project managers).</li>
 *         <li> Update the pricing plan details of a specific project (used by team members).</li>
 *     </ul>
 * </p>
 *
 * <p>
 *     All endpoints are versioned under <code>/api/v1</code> and follow RESTful conventions.
 *     The controller relies on {@link PricingPlanService} to execute business logic and uses
 *     {@link HttpResponse} utility for unified response formatting.
 * </p>
 *
 * <p>
 *     Dependencies are injected using constructor-based dependency injection (Lombok's {@code @RequiredArgsConstructor})
 *     and {@code @Autowired} for compatibility with Spring context initialization.
 * </p>
 *
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-05-14
 */
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class PricingPlanController {

	 /* Initialize dependencies */
	 @Autowired private final PricingPlanService pricingPlanService;

	 /**
		* Endpoint to request a pricing plan for the specified project.
		* <p>
		*     This operation is intended for Project Managers (PM) to initiate the pricing plan workflow
		*     by setting the pricing status of a project to {@code REQUESTED}. It acts as a signal
		*     for the next responsible team to begin evaluating or updating the pricing details.
		* </p>
		*
		* @param projectId The UUID of the target project whose pricing plan is being requested.
		* @return A {@link ResponseEntity} containing a success message and a {@link com.api.growin.dto.response.GeneralResponse}
		*         object representing the result of the update.
		*
		* @throws com.api.growin.exceptions.ProjectNotFoundException if the project ID is invalid or not found.
		*/
	 @PatchMapping("/pm/project/pricing-plan/request/{projectId}")
	 public ResponseEntity<Object> requestPricingPlan(@PathVariable UUID projectId) {
			/* Request pricing plan of the project */
			GeneralResponse requestPricingPlan = pricingPlanService.requestPricingPlan(projectId.toString());
			return HttpResponse.successResponse("Successfully updating overview", requestPricingPlan);
	 }

	 /**
		* Endpoint to update the pricing plan of a specified project.
		* <p>
		*     This operation is intended for Team Members (TM) responsible for defining or modifying
		*     a project's pricing strategy and benchmark values. It updates the corresponding
		*     {@code ProjectPricingPlan} entity in the database.
		* </p>
		*
		* @param projectId The UUID of the target project whose pricing plan is being updated.
		* @param request A {@link com.api.growin.dto.request.projects.ProjectPricingPlanRequest} object
		*                containing the updated pricing strategy and benchmark values.
		* @return A {@link ResponseEntity} containing a success message and a {@link com.api.growin.dto.response.GeneralResponse}
		*         representing the result of the update.
		*
		* @throws com.api.growin.exceptions.ProjectNotFoundException if the project is not found.
		* @throws jakarta.persistence.EntityNotFoundException if the corresponding pricing plan entity does not exist.
		*/
	 @PatchMapping("/fm/project/pricing-plan/{projectId}")
	 public ResponseEntity<Object> updatePricingPlan(@PathVariable UUID projectId, @RequestBody @Valid ProjectPricingPlanRequest request) {
			/* Update a new pricing plan */
			GeneralResponse uploadProjectComplienceDocuments = pricingPlanService.updateProjectPricingPlan(projectId.toString(), request);
			return HttpResponse.successResponse("Successfully updating overview", uploadProjectComplienceDocuments);
	 }

	 @PatchMapping("/fm/project/master-data/")
	 public ResponseEntity<Object> createMasterData(@PathVariable UUID projectId, @Valid ProjectMasterDataRequest request) {
			/* Update a new pricing plan */
			GeneralResponse createMasterData = pricingPlanService.createMasterData(request);
			return HttpResponse.successResponse("Successfully updating overview", createMasterData);
	 }

	 @PatchMapping("/fm/project/master-package/")
	 public ResponseEntity<Object> createMasterpackage(@PathVariable UUID projectId, @Valid ProjectMasterPackageRequest request) {
			/* Update a new pricing plan */
			GeneralResponse createMasterpackage = pricingPlanService.createMasterPackage(request);
			return HttpResponse.successResponse("Successfully updating overview", createMasterpackage);
	 }
}
