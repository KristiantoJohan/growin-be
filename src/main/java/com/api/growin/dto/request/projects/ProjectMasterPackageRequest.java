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
public class ProjectMasterPackageRequest {
	 @NotNull
	 private String name;

	 @NotNull
	 private String packageLevel;

	 @NotNull
	 private Long price;
}
