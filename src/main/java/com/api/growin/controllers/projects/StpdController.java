package com.api.growin.controllers.projects;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.growin.dto.request.projects.ProjectSegmentingTargetingRequest;
import com.api.growin.dto.request.projects.ProjectStpdRequest;
import com.api.growin.dto.response.GeneralResponse;
import com.api.growin.services.projects.StpdService;
import com.api.growin.utils.HttpResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/mm/project/stpd")
@RequiredArgsConstructor
public class StpdController {
	/*
	 * Initialize dependencies
	 */
	@Autowired private StpdService stpdService;
	
	@PatchMapping("/{projectid}")
	public ResponseEntity<Object> updateProjectStpd(@PathVariable UUID projectId, @Valid @RequestBody ProjectStpdRequest request) {
		/* Update the selected project stpd */
		GeneralResponse response = stpdService.updateProjectStepStpd(request, projectId.toString());
		
		/* Sending the response */
		return HttpResponse.successResponse("Successfully updating the selected project stpd", response);
	}
	
	@PostMapping("/segmenting-targeting/{projectid}")
	public ResponseEntity<Object> createSegmentingTargeting(@PathVariable UUID projectid, @Valid @RequestBody List<ProjectSegmentingTargetingRequest> request) {
		/* Update the selected project stpd */
		GeneralResponse response = this.stpdService.createSegmentingTargetingData(request, projectid.toString());
		
		/* Sending the response */
		return HttpResponse.successResponse("Successfully adding new segmenting targeting", response);
	}
}
