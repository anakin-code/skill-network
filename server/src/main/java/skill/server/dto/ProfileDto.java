package skill.server.dto;

public class ProfileDto {

    private String hrid;

    private String name;

    private String mailAddress;

    private String rankName;

    private String skills;

    private Integer years;

    private String divisionName;

    public ProfileDto(
            String hrid,
            String name,
            String mailAddress,
            String rankName,
            String skills,
            Integer years,
            String divisionName
    ) {
        this.hrid = hrid;
        this.name = name;
        this.mailAddress = mailAddress;
        this.rankName = rankName;
        this.skills = skills;
        this.years = years;
        this.divisionName = divisionName;
    }

    public String getHrid() {
        return hrid;
    }

    public void setHrid(String hrid) {
        this.hrid = hrid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}