package challenge.backend.forohub.api.forohub_backend.domain.user;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import challenge.backend.forohub.api.forohub_backend.domain.profile.Profile;

public record DataUserList(
    String name,
    String status,
    @JsonIgnoreProperties("id") Set<Profile> profiles
) {
    public DataUserList(UserEntity userEntity){
        this(
            userEntity.getName(),
            Boolean.TRUE.equals(userEntity.getActive())?"Active":"Inactive",
            userEntity.getProfiles()
        );
    }
}
