package server.dto.response;

import java.time.LocalDate;
import java.util.List;

public class CareerResponse {
    private Long careerId;
    private Long projectId;
    private String projectName;
    private String projectContent;
    private String divisionName;
    private String companyName;
    private LocalDate startTime;
    private LocalDate endTime;
    private List<String> skillNames;
    public CareerResponse(Long careerId, Long projectId, String projectName, String projectContent, String divisionName, String companyName, LocalDate startTime, LocalDate endTime, List<String> skillNames) {
        this.careerId = careerId; this.projectId = projectId; this.projectName = projectName; this.projectContent = projectContent; this.divisionName = divisionName; this.companyName = companyName; this.startTime = startTime; this.endTime = endTime; this.skillNames = skillNames;
    }
    public Long getCareerId() { return careerId; }
    public Long getProjectId() { return projectId; }
    public String getProjectName() { return projectName; }
    public String getProjectContent() { return projectContent; }
    public String getDivisionName() { return divisionName; }
    public String getCompanyName() { return companyName; }
    public LocalDate getStartTime() { return startTime; }
    public LocalDate getEndTime() { return endTime; }
    public List<String> getSkillNames() { return skillNames; }
}
