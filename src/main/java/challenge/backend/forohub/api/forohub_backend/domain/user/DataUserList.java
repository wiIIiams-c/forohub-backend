package challenge.backend.forohub.api.forohub_backend.domain.user;

import java.util.List;

import challenge.backend.forohub.api.forohub_backend.domain.profile.Profile;

public record DataUserList(
    String name,
    String status,
    List<String> profiles,
    int qtyCreatedTopics,
    int qtyAnsweredTopics
) {
    public DataUserList(UserEntity userEntity){
        this(
            userEntity.getName(),
            Boolean.TRUE.equals(userEntity.getActive())?"Active":"Inactive",
            userEntity.getProfiles().stream().map(Profile::getName).toList(),
            userEntity.getTopics().size(),
            userEntity.getAnswers().size()
        );
    }
}
