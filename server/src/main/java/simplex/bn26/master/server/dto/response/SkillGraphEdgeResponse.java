package simplex.bn26.master.server.dto.response;

public class SkillGraphEdgeResponse {

    private Long source;
    private Long target;
    private Integer weight;

    public SkillGraphEdgeResponse(Long source, Long target, Integer weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Long getSource() {
        return source;
    }

    public Long getTarget() {
        return target;
    }

    public Integer getWeight() {
        return weight;
    }
}