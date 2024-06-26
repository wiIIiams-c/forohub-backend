package challenge.backend.forohub.api.forohub_backend.domain.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import challenge.backend.forohub.api.forohub_backend.domain.answer.AnswerRepository;
import challenge.backend.forohub.api.forohub_backend.domain.answer.DataAnswer;
import challenge.backend.forohub.api.forohub_backend.infra.errors.IntegrityValidation;

@Component
public class AnswerContentExists implements AnswerValidations{
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public void validateAnswer(Object data) {
        if(data instanceof DataAnswer){
            DataAnswer dataAnswer = (DataAnswer) data;

            if(dataAnswer.author() == null || dataAnswer.topic() == null || dataAnswer.message() == null){
                return;
            }

            //validar que el mensaje no exista para el topico
            var messageExist = answerRepository.findByTopicMessage(dataAnswer.topic(), dataAnswer.message());

            if(messageExist.isPresent()){
                throw new IntegrityValidation("La respuesta ya existe para el topico...");
            }
        }
    }

}
