package skill.server.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import skill.server.dto.SearchRequest;
import skill.server.dto.response.ProfileSearchResponse;

import java.util.List;

@Repository
public class SearchRepository {

    private final JdbcTemplate jdbcTemplate;

    public SearchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProfileSearchResponse> searchProfiles(SearchRequest request) {
        SearchSqlBuilder builder = new SearchSqlBuilder();

        builder.applyConditions(request);
        builder.applyGroupByAndHaving(request);

        return jdbcTemplate.query(
                builder.getSql(),
                (rs, rowNum) -> new ProfileSearchResponse(
                        rs.getString("hrid"),
                        rs.getString("name"),
                        rs.getString("mail_address"),
                        rs.getString("rank_name"),
                        rs.getString("skill_names"),
                        rs.getInt("years"),
                        rs.getInt("months"),
                        rs.getString("division_name")
                ),
                builder.getParams()
        );
    }
}