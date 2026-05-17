package skill.server.dto.response;

import java.time.LocalDate;
import java.util.List;

public class CareerResponse {

    private Long careerId;

    private Long projectId;

    private String projectContent;

    private String projectName;

    private LocalDate startTime;

    private LocalDate endTime;

    private List<String> skillNames;

    public CareerResponse(
            Long careerId,
            Long projectId,
            String projectContent,
            String projectName,
            LocalDate startTime,
            LocalDate endTime,
            List<String> skillNames
    ) {
        this.careerId = careerId;
        this.projectId = projectId;
        this.projectContent = projectContent;
        this.projectName = projectName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.skillNames = skillNames;
    }

    public CareerResponse() {
    }

    public Long getCareerId() {
        return careerId;
    }

    public void setCareerId(Long careerId) {
        this.careerId = careerId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public List<String> getSkillNames() {
        return skillNames;
    }

    public void setSkillNames(List<String> skillNames) {
        this.skillNames = skillNames;
    }
}