package simplex.bn26.master.server.dto.response;

public class SkillGraphNodeResponse {

    private Long id;
    private String label;
    private String type;

    public SkillGraphNodeResponse(Long id, String label, String type) {
        this.id = id;
        this.label = label;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }
}