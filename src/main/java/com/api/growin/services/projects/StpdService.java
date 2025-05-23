package com.api.growin.services.projects;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.growin.dto.request.projects.ProjectSegmentingTargetingRequest;
import com.api.growin.dto.request.projects.ProjectStpdRequest;
import com.api.growin.dto.response.GeneralResponse;
import com.api.growin.exceptions.ProjectNotFoundException;
import com.api.growin.models.Demand;
import com.api.growin.models.NeedsFit;
import com.api.growin.models.Project;
import com.api.growin.models.ProjectSegmentingTargeting;
import com.api.growin.models.ProjectStepStpd;
import com.api.growin.repositories.ProjectRepository;
import com.api.growin.repositories.ProjectSegmentingTargetingRepository;
import com.api.growin.repositories.ProjectStepStpdRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StpdService {
	/**
	 * 	Initialize dependencies
	 */
	private final ProjectRepository projectRepository;
	private final ProjectStepStpdRepository projectStepStpdRepository;
	private final ProjectSegmentingTargetingRepository projectSegmentingTargetingRepository;

	@Transactional
	public GeneralResponse updateProjectStepStpd(ProjectStpdRequest request, String projectId) {
		/* Check if the project ID existed */
		Project project = projectRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectNotFoundException("Selected project not found"));

		/* Get the entity of project stpd */
		ProjectStepStpd projectStepStpd = projectStepStpdRepository.findByProject(project);
		if (projectStepStpd == null) {
			throw new EntityNotFoundException("Project STPD not found");
		}

		/* Update the selected project product stpd */
		projectStepStpd.setTam(request.getTam());
		projectStepStpd.setSam(request.getSam());
		projectStepStpd.setSom(request.getSom());
		projectStepStpd.setMarketSizeDescription(request.getMarketSizeDescription());
		projectStepStpd.setPositioningTextOne(request.getPositioningTextOne());
		projectStepStpd.setPositioningTextTwo(request.getPositioningTextTwo());
		projectStepStpd.setPositioningTextThree(request.getPositioningTextThree());
		projectStepStpd.setPositioningTextFour(request.getPositioningTextFour());
		projectStepStpd.setPositioningTextFive(request.getPositioningTextFive());
		projectStepStpd.setPositioningTextSix(request.getPositioningTextSix());
		projectStepStpd.setPositioningTextSeven(request.getPositioningTextSeven());
		projectStepStpd.setDifferentiationDescription(request.getDifferentiationDescription());

		projectStepStpd = projectStepStpdRepository.save(projectStepStpd);

		return GeneralResponse.builder()
			.createdAt(projectStepStpd.getCreatedAt())
			.updateAt(projectStepStpd.getUpdatedAt())
			.build();
	}

	@Transactional
	public GeneralResponse createSegmentingTargetingData(List<ProjectSegmentingTargetingRequest> requestList, String projectId) {
		/* Check if the project ID existed */
		Project project = projectRepository.findById(UUID.fromString(projectId)).orElseThrow(() -> new ProjectNotFoundException("Selected project not found"));

		/* Store everything to database */
		for (ProjectSegmentingTargetingRequest request: requestList) {
			ProjectSegmentingTargeting projectSegmentingTargeting = ProjectSegmentingTargeting.builder()
				.project(project)
				.segment(request.getSegment())
				.characteristic(request.getCharacteristic())
				.demand(Demand.fromString(request.getDemand()))
				.needsFit(NeedsFit.fromString(request.getNeedsFit()))
				.target(request.getTarget())
				.note(request.getNote())
				.build();

			projectSegmentingTargeting = projectSegmentingTargetingRepository.save(projectSegmentingTargeting);
		}

		/* Sending the general response */
		return GeneralResponse.builder()
			.createdAt(LocalDateTime.now())
			.updateAt(LocalDateTime.now())
			.build();
	}
}
