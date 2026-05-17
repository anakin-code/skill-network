package skill.server.repository;

import skill.server.dto.SearchRequest;
import skill.server.entity.ProfileSearchField;

import java.util.ArrayList;
import java.util.List;

public class SearchSqlBuilder {

    private final StringBuilder sql;
    private final List<Object> params;

    public SearchSqlBuilder() {
        this.sql = new StringBuilder("""
                SELECT
                    p.hrid,
                    p.name,
                    p.mail_address,
                    r.rank_name,
                    COALESCE(STRING_AGG(DISTINCT s.skill_name, ', '), '') AS skill_names,
                    COALESCE(SUM(DISTINCT CASE
                        WHEN c.career_id IS NOT NULL THEN
                            EXTRACT(YEAR FROM AGE(COALESCE(c.end_time, CURRENT_DATE), c.start_time))
                        ELSE 0
                    END), 0) AS years,
                    COALESCE(SUM(DISTINCT CASE
                        WHEN c.career_id IS NOT NULL THEN
                            EXTRACT(MONTH FROM AGE(COALESCE(c.end_time, CURRENT_DATE), c.start_time))
                        ELSE 0
                    END), 0) AS months,
                    d.division_name
                FROM profile p
                LEFT JOIN rank r ON r.rank_id = p.rank_id
                LEFT JOIN career c ON c.profile_id = p.hrid
                LEFT JOIN project pr ON pr.project_id = c.project_id
                LEFT JOIN division d ON d.division_id = pr.division_id
                LEFT JOIN career_skill cs ON cs.career_id = c.career_id
                LEFT JOIN skill s ON s.skill_id = cs.skill_id
                WHERE 1 = 1
                """);

        this.params = new ArrayList<>();
    }

    public void applyConditions(SearchRequest request) {
        addCurrentProjectCondition(request);
        addSkillCondition(request);
        addDivisionCondition(request);
        addProjectCondition(request);
        addKeywordCondition(request);
    }

    private void addCurrentProjectCondition(SearchRequest request) {
        if (request.isCurrentProjectOnly()) {
            sql.append(" AND c.end_time IS NULL ");
        }
    }

    private void addSkillCondition(SearchRequest request) {
        if (request.getSkillName() != null && !request.getSkillName().isBlank()) {
            sql.append(" AND s.skill_name LIKE ? ");
            params.add("%" + request.getSkillName().trim() + "%");
        }
    }

    private void addDivisionCondition(SearchRequest request) {
        if (request.getDivisionName() != null && !request.getDivisionName().isBlank()) {
            sql.append(" AND d.division_name LIKE ? ");
            params.add("%" + request.getDivisionName().trim() + "%");
        }
    }

    private void addProjectCondition(SearchRequest request) {
        if (request.getProjectName() != null && !request.getProjectName().isBlank()) {
            sql.append(" AND pr.project_name LIKE ? ");
            params.add("%" + request.getProjectName().trim() + "%");
        }
    }

    private void addKeywordCondition(SearchRequest request) {
        String keyword = request.getKeyword();

        if (keyword == null || keyword.isBlank()) {
            return;
        }

        List<ProfileSearchField> fields = request.getFields();

        if (fields == null || fields.isEmpty()) {
            fields = List.of(
                    ProfileSearchField.HRID,
                    ProfileSearchField.NAME,
                    ProfileSearchField.MAIL_ADDRESS,
                    ProfileSearchField.FREE,
                    ProfileSearchField.PROJECT_NAME,
                    ProfileSearchField.SKILL_NAME,
                    ProfileSearchField.DIVISION_NAME,
                    ProfileSearchField.RANK_NAME
            );
        }

        String like = "%" + keyword.trim() + "%";
        List<String> conditions = new ArrayList<>();

        if (fields.contains(ProfileSearchField.HRID)) {
            conditions.add("p.hrid LIKE ?");
            params.add(like);
        }

        if (fields.contains(ProfileSearchField.NAME)) {
            conditions.add("p.name LIKE ?");
            params.add(like);
        }

        if (fields.contains(ProfileSearchField.MAIL_ADDRESS)) {
            conditions.add("p.mail_address LIKE ?");
            params.add(like);
        }

        if (fields.contains(ProfileSearchField.FREE)) {
            conditions.add("p.free LIKE ?");
            params.add(like);
        }

        if (fields.contains(ProfileSearchField.PROJECT_NAME)) {
            conditions.add("pr.project_name LIKE ?");
            params.add(like);
        }

        if (fields.contains(ProfileSearchField.SKILL_NAME)) {
            conditions.add("s.skill_name LIKE ?");
            params.add(like);
        }

        if (fields.contains(ProfileSearchField.DIVISION_NAME)) {
            conditions.add("d.division_name LIKE ?");
            params.add(like);
        }

        if (fields.contains(ProfileSearchField.RANK_NAME)) {
            conditions.add("r.rank_name LIKE ?");
            params.add(like);
        }

        if (!conditions.isEmpty()) {
            sql.append(" AND (")
                    .append(String.join(" OR ", conditions))
                    .append(") ");
        }
    }

    public void applyGroupByAndHaving(SearchRequest request) {
        sql.append("""
                GROUP BY
                    p.hrid,
                    p.name,
                    p.mail_address,
                    r.rank_name,
                    d.division_name
                """);

        if (request.getMinYears() != null) {
            sql.append("""
                    HAVING
                        COALESCE(SUM(DISTINCT CASE
                            WHEN c.career_id IS NOT NULL THEN
                                (COALESCE(c.end_time, CURRENT_DATE) - c.start_time) / 365
                            ELSE 0
                        END), 0) >= ?
                    """);

            params.add(request.getMinYears());
        }

        sql.append(" ORDER BY p.hrid ");
    }

    public String getSql() {
        return sql.toString();
    }

    public Object[] getParams() {
        return params.toArray();
    }
}