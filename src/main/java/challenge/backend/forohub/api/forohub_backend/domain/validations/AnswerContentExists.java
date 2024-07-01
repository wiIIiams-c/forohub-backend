package challenge.backend.forohub.api.forohub_backend.domain.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import challenge.backend.forohub.api.forohub_backend.domain.answer.AnswerRepository;
import challenge.backend.forohub.api.forohub_backend.domain.answer.DataAnswer;
import challenge.backend.forohub.api.forohub_backend.domain.answer.DataAnswerUpdate;
import challenge.backend.forohub.api.forohub_backend.infra.errors.IntegrityValidation;

@Component
public class AnswerContentExists implements AnswerValidations{
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public void validateAnswer(Object data) {
        Long idTopic = null;
        String message = null;

        if(data instanceof DataAnswer){
            var dataAnswer = (DataAnswer) data;
            idTopic = dataAnswer.topic();
            message = dataAnswer.message();
        }else if(data instanceof DataAnswerUpdate){
            var dataAnswerUpdate = (DataAnswerUpdate) data;
            var idAnswer = dataAnswerUpdate.idAnswer();
            var answerData = answerRepository.getReferenceById(idAnswer);
            idTopic = answerData.getTopic().getId();
            message = dataAnswerUpdate.newMessage();
        }

        //validar que el mensaje no exista para el topico
        var messageExist = answerRepository.findByTopicMessage(idTopic, message);

        if(messageExist.isPresent()){
            throw new IntegrityValidation("La respuesta ya existe para el topico...");
        }
    }

}
