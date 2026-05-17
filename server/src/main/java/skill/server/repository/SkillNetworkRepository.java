package skill.server.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import simplex.bn26.master.server.dto.response.*;
import skill.server.dto.response.*;

import java.util.List;

@Repository
public class SkillNetworkRepository {

    private final JdbcTemplate jdbcTemplate;

    public SkillNetworkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SkillGraphNodeResponse> findSkillNodes() {
        String sql = """
                SELECT
                    skill_id,
                    skill_name
                FROM skill
                ORDER BY skill_id
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new SkillGraphNodeResponse(
                        rs.getLong("skill_id"),
                        rs.getString("skill_name"),
                        "skill"
                )
        );
    }

    public List<SkillGraphEdgeResponse> findSkillEdges() {
        String sql = """
                SELECT
                    s1.skill_id AS source,
                    s2.skill_id AS target,
                    COUNT(DISTINCT c.career_id) AS weight
                FROM career c
                JOIN career_skill cs1 ON cs1.career_id = c.career_id
                JOIN career_skill cs2 ON cs2.career_id = c.career_id
                JOIN skill s1 ON s1.skill_id = cs1.skill_id
                JOIN skill s2 ON s2.skill_id = cs2.skill_id
                WHERE s1.skill_id < s2.skill_id
                GROUP BY s1.skill_id, s2.skill_id
                ORDER BY weight DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new SkillGraphEdgeResponse(
                        rs.getLong("source"),
                        rs.getLong("target"),
                        rs.getInt("weight")
                )
        );
    }

    public List<ExpertResponse> findExpertsBySkillName(String skillName) {
        String sql = """
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
                    COUNT(DISTINCT s.skill_id) * 10
                    + COALESCE(SUM(DISTINCT CASE
                        WHEN c.career_id IS NOT NULL THEN
                            (COALESCE(c.end_time, CURRENT_DATE) - c.start_time) / 365
                        ELSE 0
                    END), 0) * 5 AS score
                FROM profile p
                LEFT JOIN rank r ON r.rank_id = p.rank_id
                LEFT JOIN career c ON c.profile_id = p.hrid
                LEFT JOIN career_skill cs ON cs.career_id = c.career_id
                LEFT JOIN skill s ON s.skill_id = cs.skill_id
                WHERE s.skill_name LIKE ?
                GROUP BY
                    p.hrid,
                    p.name,
                    p.mail_address,
                    r.rank_name
                ORDER BY score DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ExpertResponse(
                        rs.getString("hrid"),
                        rs.getString("name"),
                        rs.getString("mail_address"),
                        rs.getString("rank_name"),
                        rs.getString("skill_names"),
                        rs.getInt("years"),
                        rs.getInt("months"),
                        rs.getInt("score")
                ),
                "%" + skillName + "%"
        );
    }

    public List<SimilarProfileResponse> findSimilarProfiles(String hrid, int limit) {
        String sql = """
            WITH target_skills AS (
                SELECT DISTINCT s.skill_id, s.skill_name
                FROM profile p
                JOIN career c ON c.profile_id = p.hrid
                JOIN career_skill cs ON cs.career_id = c.career_id
                JOIN skill s ON s.skill_id = cs.skill_id
                WHERE p.hrid = ?
            ),
            other_skills AS (
                SELECT DISTINCT
                    p.hrid,
                    p.name,
                    p.mail_address,
                    r.rank_name,
                    s.skill_id,
                    s.skill_name
                FROM profile p
                LEFT JOIN rank r ON r.rank_id = p.rank_id
                JOIN career c ON c.profile_id = p.hrid
                JOIN career_skill cs ON cs.career_id = c.career_id
                JOIN skill s ON s.skill_id = cs.skill_id
                WHERE p.hrid <> ?
            ),
            other_profiles AS (
                SELECT DISTINCT
                    hrid,
                    name,
                    mail_address,
                    rank_name
                FROM other_skills
            ),
            union_skills AS (
                SELECT
                    op.hrid,
                    os.skill_id
                FROM other_profiles op
                JOIN other_skills os ON os.hrid = op.hrid

                UNION

                SELECT
                    op.hrid,
                    ts.skill_id
                FROM other_profiles op
                CROSS JOIN target_skills ts
            ),
            grouped AS (
                SELECT
                    op.hrid,
                    op.name,
                    op.mail_address,
                    op.rank_name,
                    STRING_AGG(DISTINCT os.skill_name, ', ') AS skill_names,
                    STRING_AGG(DISTINCT ts.skill_name, ', ') AS common_skill_names,
                    COUNT(DISTINCT ts.skill_id) AS common_skill_count,
                    COUNT(DISTINCT us.skill_id) AS union_skill_count
                FROM other_profiles op
                LEFT JOIN other_skills os ON os.hrid = op.hrid
                LEFT JOIN target_skills ts ON ts.skill_id = os.skill_id
                LEFT JOIN union_skills us ON us.hrid = op.hrid
                GROUP BY
                    op.hrid,
                    op.name,
                    op.mail_address,
                    op.rank_name
            )
            SELECT
                hrid,
                name,
                mail_address,
                rank_name,
                COALESCE(skill_names, '') AS skill_names,
                COALESCE(common_skill_names, '') AS common_skill_names,
                common_skill_count,
                CASE
                    WHEN union_skill_count = 0 THEN 0
                    ELSE common_skill_count::double precision / union_skill_count
                END AS similarity
            FROM grouped
            WHERE common_skill_count > 0
            ORDER BY similarity DESC, common_skill_count DESC, hrid
            LIMIT ?
            """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new SimilarProfileResponse(
                        rs.getString("hrid"),
                        rs.getString("name"),
                        rs.getString("mail_address"),
                        rs.getString("rank_name"),
                        rs.getString("skill_names"),
                        rs.getString("common_skill_names"),
                        rs.getInt("common_skill_count"),
                        rs.getDouble("similarity")
                ),
                hrid,
                hrid,
                limit
        );
    }


    public List<PersonalSkillGraphNodeResponse> findPersonalSkillNodes(String hrid) {
        String sql = """
            WITH skill_months AS (
                SELECT
                    s.skill_id,
                    s.skill_name,
                    SUM(
                        EXTRACT(YEAR FROM AGE(COALESCE(c.end_time, CURRENT_DATE), c.start_time)) * 12
                        + EXTRACT(MONTH FROM AGE(COALESCE(c.end_time, CURRENT_DATE), c.start_time))
                    )::int AS months
                FROM profile p
                JOIN career c ON c.profile_id = p.hrid
                JOIN career_skill cs ON cs.career_id = c.career_id
                JOIN skill s ON s.skill_id = cs.skill_id
                WHERE p.hrid = ?
                GROUP BY s.skill_id, s.skill_name
            ),
            max_months AS (
                SELECT MAX(months) AS max_months
                FROM skill_months
            )
            SELECT
                sm.skill_id,
                sm.skill_name,
                sm.months,
                CASE
                    WHEN mm.max_months IS NULL OR mm.max_months = 0 THEN 0
                    ELSE sm.months::double precision / mm.max_months
                END AS normalized_size
            FROM skill_months sm
            CROSS JOIN max_months mm
            ORDER BY sm.months DESC, sm.skill_id
            """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new PersonalSkillGraphNodeResponse(
                        rs.getLong("skill_id"),
                        rs.getString("skill_name"),
                        rs.getInt("months"),
                        rs.getDouble("normalized_size")
                ),
                hrid
        );
    }

    public List<PersonalSkillGraphEdgeResponse> findPersonalSkillEdges(String hrid) {
        String sql = """
            WITH edge_months AS (
                SELECT
                    s1.skill_id AS source,
                    s2.skill_id AS target,
                    SUM(
                        EXTRACT(YEAR FROM AGE(COALESCE(c.end_time, CURRENT_DATE), c.start_time)) * 12
                        + EXTRACT(MONTH FROM AGE(COALESCE(c.end_time, CURRENT_DATE), c.start_time))
                    )::int AS months
                FROM profile p
                JOIN career c ON c.profile_id = p.hrid
                JOIN career_skill cs1 ON cs1.career_id = c.career_id
                JOIN career_skill cs2 ON cs2.career_id = c.career_id
                JOIN skill s1 ON s1.skill_id = cs1.skill_id
                JOIN skill s2 ON s2.skill_id = cs2.skill_id
                WHERE p.hrid = ?
                  AND s1.skill_id < s2.skill_id
                GROUP BY s1.skill_id, s2.skill_id
            ),
            max_months AS (
                SELECT MAX(months) AS max_months
                FROM edge_months
            )
            SELECT
                em.source,
                em.target,
                em.months,
                CASE
                    WHEN mm.max_months IS NULL OR mm.max_months = 0 THEN 0
                    ELSE em.months::double precision / mm.max_months
                END AS normalized_weight
            FROM edge_months em
            CROSS JOIN max_months mm
            ORDER BY em.months DESC, em.source, em.target
            """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new PersonalSkillGraphEdgeResponse(
                        rs.getLong("source"),
                        rs.getLong("target"),
                        rs.getInt("months"),
                        rs.getDouble("normalized_weight")
                ),
                hrid
        );
    }
}