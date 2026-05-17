package simplex.bn26.master.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simplex.bn26.master.server.dto.SearchRequest;
import simplex.bn26.master.server.dto.response.ProfileSearchResponse;
import simplex.bn26.master.server.service.SearchService;

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