package com.api.growin.dto.request.projects;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSegmentingTargetingRequest {
	@NotNull
	private String segment;
	
	@NotNull
	private String characteristic;
	
	@NotNull
	private String demand;
	
	@NotNull
	private String needsFit;
	
	@NotNull
	private Boolean target;
	
	@NotNull
	private String note;

}
