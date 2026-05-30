package server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "rank")
public class Rank {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long rankId;

    @NotBlank(message = "職位を入力してください")
    @Size(max = 255, message = "職位は255文字以内で入力してください")
    @Column(name = "rank_name", length = 255, unique = true, nullable = false)
    private String rankName;

    public Rank() {}
    public Rank(String rankName) { this.rankName = rankName; }
    public Long getRankId() { return rankId; }
    public void setRankId(Long rankId) { this.rankId = rankId; }
    public String getRankName() { return rankName; }
    public void setRankName(String rankName) { this.rankName = rankName; }
}
