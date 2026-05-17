package simplex.bn26.master.server.dto.request;

public class CreateProjectRequest {

    private String projectName;

    private String projectContent;

    private String divisionName;

    public CreateProjectRequest() {
    }

    public CreateProjectRequest(String projectName, String projectContent, String divisionName) {
        this.projectName = projectName;
        this.projectContent = projectContent;
        this.divisionName = divisionName;
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