package challenge.backend.forohub.api.forohub_backend.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    UserDetails findByEmail(String email);
    Boolean existsByEmail(String email);
}
