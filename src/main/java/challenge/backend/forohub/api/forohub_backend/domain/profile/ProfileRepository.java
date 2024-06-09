package challenge.backend.forohub.api.forohub_backend.domain.profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long>{
    Profile findByName(String name);
}
