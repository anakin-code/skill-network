package simplex.bn26.master.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplex.bn26.master.server.dto.request.CreateUserRequest;
import simplex.bn26.master.server.dto.response.ProfileResponse;
import simplex.bn26.master.server.dto.response.UserResponse;
import simplex.bn26.master.server.entity.Profile;
import simplex.bn26.master.server.entity.Rank;
import simplex.bn26.master.server.entity.User;
import simplex.bn26.master.server.repository.ProfileRepository;
import simplex.bn26.master.server.repository.RankRepository;
import simplex.bn26.master.server.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RankRepository rankRepository;
    private final ProfileRepository profileRepository;

    public UserService(
            UserRepository userRepository,
            RankRepository rankRepository,
            ProfileRepository profileRepository
    ) {
        this.userRepository = userRepository;
        this.rankRepository = rankRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User user = new User();

        user.setIsManager(request.getIsManager() != null ? request.getIsManager() : false);

        User savedUser = userRepository.save(user);

        Profile profile = new Profile();

        profile.setUser(savedUser);
        profile.setName(request.getName());
        profile.setMailAddress(request.getMailAddress());
        profile.setHrid(request.getHrid());
        profile.setFree(request.getFree());

        if (request.getRankName() != null && !request.getRankName().isBlank()) {
            Rank rank = rankRepository.findByRankName(request.getRankName())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "rank=" + request.getRankName() + " not found"
                            )
                    );
            profile.setRank(rank);
        }

        profileRepository.save(profile);

        ProfileResponse profileResponse = convertProfileResponse(profile);

        return new UserResponse(
                Math.toIntExact(savedUser.getId()),
                savedUser.getIsManager(),
                profileResponse
        );
    }

    private ProfileResponse convertProfileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getHrid(),
                profile.getName(),
                profile.getMailAddress(),
                profile.getRank() != null ? profile.getRank().getRankName() : null,
                profile.getFree(),
                List.of()
        );
    }
}