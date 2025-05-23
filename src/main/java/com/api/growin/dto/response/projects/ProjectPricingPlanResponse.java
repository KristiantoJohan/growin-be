package com.api.growin.dto.response.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPricingPlanResponse {
	/**
	 * Unique identifier for the product.
	 */
	private String id;

	/*
	 	The target price by strategy of the product
	 */
	private Long targetPriceStrategy;

	/*
	 	The target price by benchmark of the product
	 */
	private Long targetPriceBenchmark;

	/**
	 * Timestamp indicating when the product overview was created.
	 */
	private LocalDateTime createdAt;

	/**
	 * Timestamp indicating the last update of the product overview.
	 */
	private LocalDateTime updatedAt;
}
