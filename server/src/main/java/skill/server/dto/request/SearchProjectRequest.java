package skill.server.dto.request;

public class SearchProjectRequest {

    private String keyword;

    public SearchProjectRequest() {
    }

    public SearchProjectRequest(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}