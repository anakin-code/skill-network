package simplex.bn26.master.server.dto.response;

import java.util.List;

public class SkillGraphResponse {

    private List<SkillGraphNodeResponse> nodes;
    private List<SkillGraphEdgeResponse> edges;

    public SkillGraphResponse(
            List<SkillGraphNodeResponse> nodes,
            List<SkillGraphEdgeResponse> edges
    ) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<SkillGraphNodeResponse> getNodes() {
        return nodes;
    }

    public List<SkillGraphEdgeResponse> getEdges() {
        return edges;
    }
}