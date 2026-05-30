package server.dto.response;

public class SkillExperienceResponse {
    private Long skillId;
    private String skillName;
    private int totalYears;
    private int totalMonths;
    public SkillExperienceResponse(Long skillId, String skillName, int totalYears, int totalMonths) { this.skillId = skillId; this.skillName = skillName; this.totalYears = totalYears; this.totalMonths = totalMonths; }
    public Long getSkillId() { return skillId; }
    public String getSkillName() { return skillName; }
    public int getTotalYears() { return totalYears; }
    public int getTotalMonths() { return totalMonths; }
}
