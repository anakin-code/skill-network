package simplex.bn26.master.server.dto.response;

public class ProjectResponse {

    private Long projectId;

    private String projectName;

    private String projectContent;

    private String divisionName;

    public ProjectResponse() {
    }

    public ProjectResponse(
            Long projectId,
            String projectName,
            String projectContent,
            String divisionName
    ) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectContent = projectContent;
        this.divisionName = divisionName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}