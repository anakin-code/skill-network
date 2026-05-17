package skill.server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skill.server.dto.request.CreateUserRequest;
import skill.server.dto.response.ProfileResponse;
import skill.server.dto.response.UserResponse;
import skill.server.entity.Profile;
import skill.server.entity.Rank;
import skill.server.entity.User;
import skill.server.repository.ProfileRepository;
import skill.server.repository.RankRepository;
import skill.server.repository.UserRepository;

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