package simplex.bn26.master.server.dto.response;

public class UserResponse {

    private Integer userId;

    private boolean isManager;

    private ProfileResponse profile;

    public UserResponse() {
    }

    public UserResponse(
            Integer userId,
            boolean isManager,
            ProfileResponse profile
    ) {
        this.userId = userId;
        this.isManager = isManager;
        this.profile = profile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public ProfileResponse getProfile() {
        return profile;
    }

    public void setProfile(ProfileResponse profile) {
        this.profile = profile;
    }
}