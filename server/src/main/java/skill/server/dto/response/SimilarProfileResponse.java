package skill.server.dto.response;

public class SimilarProfileResponse {

    private String hrid;
    private String name;
    private String mailAddress;
    private String rankName;
    private String skillNames;
    private String commonSkillNames;
    private int commonSkillCount;
    private double similarity;

    public SimilarProfileResponse(
            String hrid,
            String name,
            String mailAddress,
            String rankName,
            String skillNames,
            String commonSkillNames,
            int commonSkillCount,
            double similarity
    ) {
        this.hrid = hrid;
        this.name = name;
        this.mailAddress = mailAddress;
        this.rankName = rankName;
        this.skillNames = skillNames;
        this.commonSkillNames = commonSkillNames;
        this.commonSkillCount = commonSkillCount;
        this.similarity = similarity;
    }

    public String getHrid() {
        return hrid;
    }

    public String getName() {
        return name;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getRankName() {
        return rankName;
    }

    public String getSkillNames() {
        return skillNames;
    }

    public String getCommonSkillNames() {
        return commonSkillNames;
    }

    public int getCommonSkillCount() {
        return commonSkillCount;
    }

    public double getSimilarity() {
        return similarity;
    }
}