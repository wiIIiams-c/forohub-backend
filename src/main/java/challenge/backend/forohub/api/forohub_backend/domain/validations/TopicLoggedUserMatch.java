package challenge.backend.forohub.api.forohub_backend.domain.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import challenge.backend.forohub.api.forohub_backend.domain.user.UserRepository;
import challenge.backend.forohub.api.forohub_backend.infra.errors.IntegrityValidation;
import challenge.backend.forohub.api.forohub_backend.infra.security.IAuthenticationFacade;

/*
 * Componente que valida que el usuario que crea el tema sea el mismo
 * que el usuario que esta logeado.
 */

@Component
public class TopicLoggedUserMatch implements TopicValidations{
    private IAuthenticationFacade authFacade;
    private UserRepository userRepository;

    @Autowired
    public TopicLoggedUserMatch(IAuthenticationFacade authFacade, UserRepository userRepository) {
        this.authFacade = authFacade;
        this.userRepository = userRepository;
    }

    @Override
    public void validateTopic(Object data) {
        if(data instanceof Long){
            var topicAuthorId = (Long) data;

            if(topicAuthorId == null || loggedUserId() == null){
                return;
            }
            
            if(!topicAuthorId.equals(loggedUserId())){
                throw new IntegrityValidation("El topic no pertenece al usuario logeado...");
            }
        }
    }

    private Long loggedUserId() {
        Authentication auth = authFacade.getAuthentication();

        return userRepository.findIdByName(auth.getName());
    }
}
