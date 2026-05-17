package skill.server.dto.response;

public class ProfileSearchResponse {

    private String hrid;
    private String name;
    private String mailAddress;
    private String rankName;
    private String skills;
    private int years;
    private int months;
    private String divisionName;

    public ProfileSearchResponse(
            String hrid,
            String name,
            String mailAddress,
            String rankName,
            String skills,
            int years,
            int months,
            String divisionName
    ) {
        this.hrid = hrid;
        this.name = name;
        this.mailAddress = mailAddress;
        this.rankName = rankName;
        this.skills = skills;
        this.years = years;
        this.months = months;
        this.divisionName = divisionName;
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

    public String getSkills() {
        return skills;
    }

    public int getYears() {
        return years;
    }

    public int getMonths() {
        return months;
    }

    public String getDivisionName() {
        return divisionName;
    }
}