package simplex.bn26.master.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplex.bn26.master.server.dto.SearchRequest;
import simplex.bn26.master.server.dto.response.ProfileSearchResponse;
import simplex.bn26.master.server.repository.SearchRepository;

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