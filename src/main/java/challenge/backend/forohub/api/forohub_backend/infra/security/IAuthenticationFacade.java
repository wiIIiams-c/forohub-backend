package challenge.backend.forohub.api.forohub_backend.infra.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
