package server.dto.response;

public class SimilarProfileResponse {
    private String hrid;
    private String name;
    private double similarity;
    private String commonSkillNames;

    public SimilarProfileResponse(String hrid, String name, double similarity, String commonSkillNames) {
        this.hrid = hrid;
        this.name = name;
        this.similarity = similarity;
        this.commonSkillNames = commonSkillNames;
    }

    public String getHrid() { return hrid; }
    public String getName() { return name; }
    public double getSimilarity() { return similarity; }
    public String getCommonSkillNames() { return commonSkillNames; }
}
