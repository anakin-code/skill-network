package simplex.bn26.master.server.dto.response;

import java.util.List;

public class PersonalSkillGraphResponse {

    private String hrid;
    private List<PersonalSkillGraphNodeResponse> nodes;
    private List<PersonalSkillGraphEdgeResponse> edges;

    public PersonalSkillGraphResponse(
            String hrid,
            List<PersonalSkillGraphNodeResponse> nodes,
            List<PersonalSkillGraphEdgeResponse> edges
    ) {
        this.hrid = hrid;
        this.nodes = nodes;
        this.edges = edges;
    }

    public String getHrid() {
        return hrid;
    }

    public List<PersonalSkillGraphNodeResponse> getNodes() {
        return nodes;
    }

    public List<PersonalSkillGraphEdgeResponse> getEdges() {
        return edges;
    }
}