package skill.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skill.server.dto.response.ExpertResponse;
import skill.server.dto.response.PersonalSkillGraphResponse;
import skill.server.dto.response.SkillGraphResponse;
import skill.server.dto.response.SimilarProfileResponse;
import skill.server.repository.SkillNetworkRepository;

import java.util.List;

@Service
@Transactional
public class SkillNetworkService {

    private final SkillNetworkRepository skillNetworkRepository;

    public SkillNetworkService(SkillNetworkRepository skillNetworkRepository) {
        this.skillNetworkRepository = skillNetworkRepository;
    }

    public SkillGraphResponse getSkillGraph() {
        return new SkillGraphResponse(
                skillNetworkRepository.findSkillNodes(),
                skillNetworkRepository.findSkillEdges()
        );
    }

    public List<ExpertResponse> getExpertsBySkillName(String skillName) {
        return skillNetworkRepository.findExpertsBySkillName(skillName);
    }

    public PersonalSkillGraphResponse getPersonalSkillGraph(String hrid) {
        return new PersonalSkillGraphResponse(
                hrid,
                skillNetworkRepository.findPersonalSkillNodes(hrid),
                skillNetworkRepository.findPersonalSkillEdges(hrid)
        );
    }

    public List<SimilarProfileResponse> getSimilarProfiles(
            String hrid,
            Integer limit
    ) {
        int actualLimit = limit == null ? 5 : limit;

        return skillNetworkRepository.findSimilarProfiles(
                hrid,
                actualLimit
        );
    }
}