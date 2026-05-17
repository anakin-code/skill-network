package skill.server.dto;

import skill.server.entity.ProfileSearchField;

import java.util.List;

public class SearchRequest {

    private String keyword;

    private List<ProfileSearchField> fields;

    private String skillName;

    private Double minYears;

    private String divisionName;

    private boolean currentProjectOnly;

    private String projectName;

    public SearchRequest() {
    }

    public SearchRequest(String keyword, List<ProfileSearchField> fields) {
        this.keyword = keyword;
        this.fields = fields;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<ProfileSearchField> getFields() {
        return fields;
    }

    public void setFields(List<ProfileSearchField> fields) {
        this.fields = fields;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Double getMinYears() {
        return minYears;
    }

    public void setMinYears(Double minYears) {
        this.minYears = minYears;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public boolean isCurrentProjectOnly() {
        return currentProjectOnly;
    }

    public void setCurrentProjectOnly(boolean currentProjectOnly) {
        this.currentProjectOnly = currentProjectOnly;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}