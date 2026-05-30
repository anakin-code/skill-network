package server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @Size(max = 8, message = "HRIDは8文字以内で入力してください")
    @Column(name = "hrid", length = 8)
    private String hrid;

    @NotBlank(message = "名前を入力してください")
    @Size(max = 38, message = "名前は38文字以内で入力してください")
    @Column(name = "name", length = 38, nullable = false)
    private String name;

    @Email(message = "メールアドレスを入力してください")
    @Column(name = "mail_address", unique = true)
    private String mailAddress;

    @Size(max = 200, message = "200文字以内で入力してください")
    @Column(name = "free", length = 200)
    private String free;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id")
    private Rank rank;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Career> careers = new ArrayList<>();

    public Profile() {}
    public Profile(String hrid, String name, String mailAddress, String free, User user, Rank rank) {
        this.hrid = hrid; this.name = name; this.mailAddress = mailAddress; this.free = free; this.user = user; this.rank = rank;
    }
    public String getHrid() { return hrid; }
    public void setHrid(String hrid) { this.hrid = hrid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMailAddress() { return mailAddress; }
    public void setMailAddress(String mailAddress) { this.mailAddress = mailAddress; }
    public String getFree() { return free; }
    public void setFree(String free) { this.free = free; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Rank getRank() { return rank; }
    public void setRank(Rank rank) { this.rank = rank; }
    public List<Career> getCareers() { return careers; }
}
