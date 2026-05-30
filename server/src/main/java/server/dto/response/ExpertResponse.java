package server.dto.response;

public class ExpertResponse {
    private String hrid;
    private String name;
    private String mailAddress;
    private String rankName;
    private int totalMonths;

    public ExpertResponse(String hrid, String name, String mailAddress, String rankName, int totalMonths) {
        this.hrid = hrid;
        this.name = name;
        this.mailAddress = mailAddress;
        this.rankName = rankName;
        this.totalMonths = totalMonths;
    }

    public String getHrid() { return hrid; }
    public String getName() { return name; }
    public String getMailAddress() { return mailAddress; }
    public String getRankName() { return rankName; }
    public int getTotalMonths() { return totalMonths; }
}
