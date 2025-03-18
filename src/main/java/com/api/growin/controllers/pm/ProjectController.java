package com.api.growin.controllers.pm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.growin.dto.response.pm.project.ProjectInitResponse;
import com.api.growin.models.User;

import com.api.growin.services.pm.ProjectService;
import com.api.growin.utils.HttpResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/pm/project")
@RequiredArgsConstructor
public class ProjectController {

    @Autowired
    private final ProjectService projectService;

    @PostMapping("/")
    public ResponseEntity<Object> initializeProject(@AuthenticationPrincipal User user) {
        ProjectInitResponse response = projectService.projectInit(user.getId().toString());
        return HttpResponse.successResponse("Successfully initialize new project", response);
    }
}
