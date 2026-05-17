package simplex.bn26.master.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplex.bn26.master.server.dto.request.CreateProjectRequest;
import simplex.bn26.master.server.dto.response.ProjectResponse;
import simplex.bn26.master.server.entity.Division;
import simplex.bn26.master.server.entity.Project;
import simplex.bn26.master.server.repository.DivisionRepository;
import simplex.bn26.master.server.repository.ProjectRepository;

import java.util.List;
import simplex.bn26.master.server.dto.request.UpdateProjectRequest;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final DivisionRepository divisionRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            DivisionRepository divisionRepository
    ) {
        this.projectRepository = projectRepository;
        this.divisionRepository = divisionRepository;
    }

    public List<ProjectResponse> getProjectAll() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(project -> new ProjectResponse(
                        project.getProjectId(),
                        project.getProjectName(),
                        project.getProjectContent(),
                        project.getDivision() != null
                                ? project.getDivision().getDivisionName()
                                : "no division"
                ))
                .toList();
    }

    public List<ProjectResponse> getProjectByDivisionName(String divisionName) {
        List<Project> projects = projectRepository.findByDivisionDivisionName(divisionName);

        return projects.stream()
                .map(project -> new ProjectResponse(
                        project.getProjectId(),
                        project.getProjectName(),
                        project.getProjectContent(),
                        project.getDivision() != null
                                ? project.getDivision().getDivisionName()
                                : "no division"
                ))
                .toList();
    }

    public ProjectResponse createProject(CreateProjectRequest request) {
        Project project = new Project();

        project.setProjectName(request.getProjectName());
        project.setProjectContent(request.getProjectContent());

        Division division = divisionRepository
                .findByDivisionName(request.getDivisionName())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "[" + request.getDivisionName() + "] not found"
                        )
                );

        project.setDivision(division);

        Project savedProject = projectRepository.save(project);

        return new ProjectResponse(
                savedProject.getProjectId(),
                savedProject.getProjectName(),
                savedProject.getProjectContent(),
                request.getDivisionName()
        );
    }

    public void deleteProjectByProjectName(String name) {
        Project project = projectRepository
                .findByProjectName(name)
                .orElseThrow(() ->
                        new IllegalArgumentException("[" + name + "] not found")
                );

        projectRepository.deleteById(project.getProjectId());
    }

    public ProjectResponse updateProject(Long projectId, UpdateProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new IllegalArgumentException("project not found")
                );

        if (request.getProjectName() != null && !request.getProjectName().isBlank()) {
            project.setProjectName(request.getProjectName());
        }

        if (request.getProjectContent() != null) {
            project.setProjectContent(request.getProjectContent());
        }

        if (request.getDivisionName() != null && !request.getDivisionName().isBlank()) {
            Division division = divisionRepository
                    .findByDivisionName(request.getDivisionName())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "[" + request.getDivisionName() + "] not found"
                            )
                    );

            project.setDivision(division);
        }

        Project savedProject = projectRepository.save(project);

        return new ProjectResponse(
                savedProject.getProjectId(),
                savedProject.getProjectName(),
                savedProject.getProjectContent(),
                savedProject.getDivision() != null
                        ? savedProject.getDivision().getDivisionName()
                        : "no division"

        );
    }
}