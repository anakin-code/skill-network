package server.dto.response;

import java.util.List;

public class PersonalSkillGraphResponse {
    private List<PersonalSkillNodeResponse> nodes;
    private List<PersonalSkillEdgeResponse> edges;

    public PersonalSkillGraphResponse(List<PersonalSkillNodeResponse> nodes, List<PersonalSkillEdgeResponse> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<PersonalSkillNodeResponse> getNodes() { return nodes; }
    public List<PersonalSkillEdgeResponse> getEdges() { return edges; }
}
