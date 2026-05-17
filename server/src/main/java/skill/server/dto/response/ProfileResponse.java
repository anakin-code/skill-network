package skill.server.dto.response;

import java.util.List;

public class ProfileResponse {

    private String hrid;

    private String name;

    private String mailAddress;

    private String rankName;

    private String free;

    private List<CareerResponse> careers;

    public ProfileResponse(
            String hrid,
            String name,
            String mailAddress,
            String rankName,
            String free,
            List<CareerResponse> careers
    ) {
        this.hrid = hrid;
        this.name = name;
        this.mailAddress = mailAddress;
        this.rankName = rankName;
        this.free = free;
        this.careers = careers;
    }

    public ProfileResponse() {
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

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public List<CareerResponse> getCareers() {
        return careers;
    }

    public void setCareers(List<CareerResponse> careers) {
        this.careers = careers;
    }
}