package challenge.backend.forohub.api.forohub_backend.domain.services;

import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import challenge.backend.forohub.api.forohub_backend.domain.profile.ProfileRepository;
import challenge.backend.forohub.api.forohub_backend.domain.user.DataRegisterUser;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserEntity;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserRepository;

@Service
public class UserEntityService {
    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserEntityService(UserRepository userRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean registerUser(DataRegisterUser dataRegisterUser){
        if(dataRegisterUser.email() != null && userRepository.existsByEmail(dataRegisterUser.email()).booleanValue()){
            return false;
        }

        var dataProfile = profileRepository.findByName("ROLE_USER");
        var newUser = new UserEntity(
            dataRegisterUser.name(),
            dataRegisterUser.email(),
            passwordEncoder.encode(dataRegisterUser.password()),
            Set.of(dataProfile)
        );

        userRepository.save(newUser);

        return true;
    }
}
