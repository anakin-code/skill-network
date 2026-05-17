package simplex.bn26.master.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simplex.bn26.master.server.dto.request.CreateProjectRequest;
import simplex.bn26.master.server.dto.request.SearchProjectRequest;
import simplex.bn26.master.server.dto.request.UpdateProjectRequest;
import simplex.bn26.master.server.dto.response.ProjectResponse;
import simplex.bn26.master.server.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(
            ProjectService projectService
    ) {
        this.projectService = projectService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponse>> getProjectAll() {
        List<ProjectResponse> responses = projectService.getProjectAll();

        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/division")
    public ResponseEntity<List<ProjectResponse>> searchProjectByDivision(
            @RequestBody SearchProjectRequest request
    ) {
        List<ProjectResponse> results =
                projectService.getProjectByDivisionName(request.getKeyword());

        return ResponseEntity.ok(results);
    }

    @PostMapping("/create")
    public ProjectResponse createProject(
            @RequestBody CreateProjectRequest request
    ) {
        return projectService.createProject(request);
    }

    @DeleteMapping("/{projectName}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable String projectName
    ) {
        projectService.deleteProjectByProjectName(projectName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{projectId}")
    public ProjectResponse updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest request
    ) {
        return projectService.updateProject(projectId, request);
    }
}