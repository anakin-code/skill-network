package server.dto.response;

import java.util.List;

public class ProfileResponse {
    private String hrid;
    private String name;
    private String mailAddress;
    private String free;
    private String rankName;
    private List<CareerResponse> careers;
    private List<SkillExperienceResponse> skillExperiences;
    public ProfileResponse(String hrid, String name, String mailAddress, String free, String rankName, List<CareerResponse> careers, List<SkillExperienceResponse> skillExperiences) {
        this.hrid = hrid; this.name = name; this.mailAddress = mailAddress; this.free = free; this.rankName = rankName; this.careers = careers; this.skillExperiences = skillExperiences;
    }
    public String getHrid() { return hrid; }
    public String getName() { return name; }
    public String getMailAddress() { return mailAddress; }
    public String getFree() { return free; }
    public String getRankName() { return rankName; }
    public List<CareerResponse> getCareers() { return careers; }
    public List<SkillExperienceResponse> getSkillExperiences() { return skillExperiences; }
}
