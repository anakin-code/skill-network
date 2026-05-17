package skill.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skill.server.dto.SearchRequest;
import skill.server.dto.response.ProfileSearchResponse;
import skill.server.service.SearchService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(
            SearchService searchService
    ) {
        this.searchService = searchService;
    }

    @PostMapping("/profiles")
    public ResponseEntity<?> searchProfiles(
            @RequestBody SearchRequest request
    ) {
        List<ProfileSearchResponse> results =
                searchService.searchProfiles(request);

        if (results.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "検索結果がありませんでした"));
        }

        return ResponseEntity.ok(results);
    }
}