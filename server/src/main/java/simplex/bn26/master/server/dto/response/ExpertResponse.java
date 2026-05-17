package simplex.bn26.master.server.dto.response;

public class ExpertResponse {

    private String hrid;
    private String name;
    private String mailAddress;
    private String rankName;
    private String skillNames;
    private int years;
    private int months;
    private int score;

    public ExpertResponse(
            String hrid,
            String name,
            String mailAddress,
            String rankName,
            String skillNames,
            int years,
            int months,
            int score
    ) {
        this.hrid = hrid;
        this.name = name;
        this.mailAddress = mailAddress;
        this.rankName = rankName;
        this.skillNames = skillNames;
        this.years = years;
        this.months = months;
        this.score = score;
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

    public int getYears() {
        return years;
    }

    public int getMonths() {
        return months;
    }

    public int getScore() {
        return score;
    }
}