package skill.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "rank")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private int rankId;

    @NotBlank(message = "役職名を入力してください")
    @Size(max = 255, message = "役職名は255文字以内で入力してください")
    @Column(name = "rank_name", length = 255, unique = true)
    private String rankName;

    public Rank() {
    }

    public Rank(String rankName, int rankId) {
        this.rankName = rankName;
        this.rankId = rankId;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }
}