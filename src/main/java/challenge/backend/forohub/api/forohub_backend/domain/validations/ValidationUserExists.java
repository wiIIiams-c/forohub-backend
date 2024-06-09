package challenge.backend.forohub.api.forohub_backend.domain.validations;

import challenge.backend.forohub.api.forohub_backend.domain.user.DataAuthenticationUser;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserRepository;

public class ValidationUserExists {
    private UserRepository userRepository;

    public void isUserExists(DataAuthenticationUser dataAuthUser){
        var user = userRepository.findByEmail(dataAuthUser.email());
    }
}
