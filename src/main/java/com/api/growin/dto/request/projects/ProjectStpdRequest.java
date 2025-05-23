package com.api.growin.dto.request.projects;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStpdRequest {
	@Min(value = 0, message = "Minimum value is 0")
	private Long tam;
	
	@Min(value = 0, message = "Minimum value is 0")
	private Long sam;
	
	@Min(value = 0, message = "Minimum value is 0")
	private Long som;
	
	private String marketSizeDescription;
	
	private String positioningTextOne;
	
	private String positioningTextTwo;
	
	private String positioningTextThree;
	
	private String positioningTextFour;
	
	private String positioningTextFive;
	
	private String positioningTextSix;
	
	private String positioningTextSeven;
	
	private String differentiationDescription;
}
