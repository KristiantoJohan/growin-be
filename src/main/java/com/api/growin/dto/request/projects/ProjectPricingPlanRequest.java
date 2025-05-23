package com.api.growin.dto.request.projects;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload for submitting or updating the pricing plan of a project.
 * <p>
 *     This DTO captures target price-related information for a project, specifically
 *     the pricing strategy and market benchmark values. These fields help define
 *     pricing decisions based on internal strategies and external market standards.
 * </p>
 *
 * <p>
 *     This class uses Lombok to reduce boilerplate code, and Jakarta Bean Validation
 *     annotations to enforce minimum value constraints on price-related fields.
 * </p>
 *
 * @author Johan Kristianto
 * @version 1.0
 * @since 2025-05-13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPricingPlanRequest {
	/*
	 	The target price by strategy of the product
	 */
	@Min(value = 0, message = "Minimum value is 0")
	private Long targetPriceStrategy;

	/*
	 	The target price by benchmark of the product
	 */
	@Min(value = 0, message = "Minimum value is 0")
	private Long targetPriceBenchmark;
}
