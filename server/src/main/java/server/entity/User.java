package server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_manager", nullable = false)
    private boolean manager;

    public User() {}
    public User(boolean manager) { this.manager = manager; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public boolean isManager() { return manager; }
    public void setManager(boolean manager) { this.manager = manager; }
}
