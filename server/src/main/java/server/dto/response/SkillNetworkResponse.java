package server.dto.response;

import java.util.List;

public class SkillNetworkResponse {
    private List<SkillNodeResponse> nodes;
    private List<SkillEdgeResponse> edges;
    public SkillNetworkResponse(List<SkillNodeResponse> nodes, List<SkillEdgeResponse> edges) { this.nodes = nodes; this.edges = edges; }
    public List<SkillNodeResponse> getNodes() { return nodes; }
    public List<SkillEdgeResponse> getEdges() { return edges; }
}
