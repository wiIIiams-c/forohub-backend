package challenge.backend.forohub.api.forohub_backend.domain.user;

import jakarta.validation.constraints.Email;

public record DataAuthenticationUser(
    @Email String email,
    String password
) {

}
