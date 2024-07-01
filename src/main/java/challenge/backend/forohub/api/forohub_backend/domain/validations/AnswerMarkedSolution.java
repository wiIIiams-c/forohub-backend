package challenge.backend.forohub.api.forohub_backend.domain.validations;

import org.springframework.stereotype.Component;

import challenge.backend.forohub.api.forohub_backend.domain.answer.Answer;
import challenge.backend.forohub.api.forohub_backend.infra.errors.IntegrityValidation;

@Component
public class AnswerMarkedSolution implements AnswerValidations{

    @Override
    public void validateAnswer(Object data) {
        if(data instanceof Answer){
            Answer answer = (Answer) data;

            if(answer.getSolution().booleanValue()){
                throw new IntegrityValidation("La respuesta ya ha sido marcada como solucion...");
            }
        }
    }

}
