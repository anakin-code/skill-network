package skill.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skill.server.dto.SearchRequest;
import skill.server.dto.response.ProfileSearchResponse;
import skill.server.repository.SearchRepository;

import java.util.List;

@Service
@Transactional
public class SearchService {

    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<ProfileSearchResponse> searchProfiles(SearchRequest request) {
        return searchRepository.searchProfiles(request);
    }
}