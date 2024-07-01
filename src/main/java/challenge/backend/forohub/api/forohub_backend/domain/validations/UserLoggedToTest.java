package challenge.backend.forohub.api.forohub_backend.domain.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import challenge.backend.forohub.api.forohub_backend.domain.answer.Answer;
import challenge.backend.forohub.api.forohub_backend.domain.topic.Topic;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserRepository;
import challenge.backend.forohub.api.forohub_backend.infra.errors.IntegrityValidation;
import challenge.backend.forohub.api.forohub_backend.infra.security.IAuthenticationFacade;

@Component
public class UserLoggedToTest implements GeneralValidations{
    private IAuthenticationFacade authFacade;
    private UserRepository userRepository;

    @Autowired
    public UserLoggedToTest(IAuthenticationFacade authFacade, UserRepository userRepository) {
        this.authFacade = authFacade;
        this.userRepository = userRepository;
    }
    
    private Long loggedUserId() {
        Authentication auth = authFacade.getAuthentication();

        return userRepository.findIdByName(auth.getName());
    }

    @Override
    public void validateGeneral(Object data) {
        Long dataToTest = null;

        if(data instanceof Topic){
            var topicData = (Topic) data;
            dataToTest = topicData.getAuthor().getId();
        }else if(data instanceof Answer){
            var answerData = (Answer) data;
            dataToTest = answerData.getAuthor().getId();
        }
        
        if(!dataToTest.equals(loggedUserId())){
            throw new IntegrityValidation("El usuario no puede realizar esta accion...");
        }
    }
}
