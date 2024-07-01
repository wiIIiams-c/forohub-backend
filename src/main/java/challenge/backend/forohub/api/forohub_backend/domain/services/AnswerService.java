package challenge.backend.forohub.api.forohub_backend.domain.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.backend.forohub.api.forohub_backend.domain.answer.Answer;
import challenge.backend.forohub.api.forohub_backend.domain.answer.AnswerRepository;
import challenge.backend.forohub.api.forohub_backend.domain.answer.DataAnswer;
import challenge.backend.forohub.api.forohub_backend.domain.answer.DataAnswerUpdate;
import challenge.backend.forohub.api.forohub_backend.domain.answer.DataSavedAnswer;
import challenge.backend.forohub.api.forohub_backend.domain.topic.Topic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.TopicRepository;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserEntity;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserRepository;
import challenge.backend.forohub.api.forohub_backend.domain.validations.AnswerValidations;
import challenge.backend.forohub.api.forohub_backend.domain.validations.GeneralValidations;
import challenge.backend.forohub.api.forohub_backend.domain.validations.TopicValidations;
import challenge.backend.forohub.api.forohub_backend.infra.errors.IntegrityValidation;
import jakarta.validation.Valid;

@Service
public class AnswerService {
    private TopicRepository topicRepository;
    private AnswerRepository answerRepository;
    private UserRepository userRepository;
    private List<AnswerValidations> answerValidations;
    private List<TopicValidations> topicValidations;
    private List<GeneralValidations> generalValidations;

    @Autowired
    public AnswerService(TopicRepository topicRepository, AnswerRepository answerRepository, UserRepository userRepository, 
                        List<AnswerValidations> answerValidations, List<TopicValidations> topicValidations, List<GeneralValidations> generalValidations) {
        this.topicRepository = topicRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.answerValidations = answerValidations;
        this.topicValidations = topicValidations;
        this.generalValidations = generalValidations;
    }
    
    public DataSavedAnswer addNewAnswer(@Valid DataAnswer dataAnswer) {
        var methodTopic = getSelectedTopic(dataAnswer);
        var methodUser = getSelectedUser(dataAnswer);
        var currentDate = LocalDateTime.now();

        answerValidations.forEach(av -> av.validateAnswer(dataAnswer));
        topicValidations.forEach(tv -> tv.validateTopic(dataAnswer));

        var newAnswer = new Answer(
            dataAnswer.message(),
            currentDate,
            methodTopic,
            methodUser
        );

        answerRepository.save(newAnswer);

        return new DataSavedAnswer(newAnswer);
    }

    private Topic getSelectedTopic(DataAnswer data){
        //valida que el topico a responder existe
        if(!topicRepository.existsById(data.topic())){
            throw new IntegrityValidation("El topic a responder no existe...");
        }

        return topicRepository.getReferenceById(data.topic());
    }

    private UserEntity getSelectedUser(DataAnswer data){
        //valida que el usuario que responde exista
        if(!userRepository.existsById(data.author())){
            throw new IntegrityValidation("El usuario ingresado es invalido...");
        }

        return userRepository.getReferenceById(data.author());
    }

    public String solutionAnswer(@Valid Long id) {        
        if(id == null){
            throw new IntegrityValidation("Ha ocurrido un problema con los parametros enviados...");
        }

        if(!answerRepository.existsById(id)){
            throw new IntegrityValidation("La respuesta a marcar no existe...");
        }

        var answerData = answerRepository.getReferenceById(id);
        var topicData = topicRepository.getReferenceById(answerData.getTopic().getId());

        //valida si la respuesta ya esta marcada como solucion
        answerValidations.forEach(av -> av.validateAnswer(answerData));

        //verificar que el usuario que marca solucion sea creador del topic
        topicValidations.forEach(tv -> tv.validateTopic(topicData));

        //se marca la respuesta como solucion
        answerData.markSolution();

        return "Se ha marcado la respuesta como solucion...";
    }

    public String deleteAnswer(@Valid Long id) {
        if(id == null){
            throw new IntegrityValidation("Ha ocurrido un problema con los parametros enviados...");
        }

        if(!answerRepository.existsById(id)){
            throw new IntegrityValidation("La respuesta a eliminar no existe...");
        }
        
        var answerData = answerRepository.getReferenceById(id);

        generalValidations.forEach(gv -> gv.validateGeneral(answerData));
        answerValidations.forEach(av -> av.validateAnswer(answerData));

        answerRepository.deleteById(id);        
        
        return "La respuesta ha sido eliminada...";
    }

    public Answer updateAnswer(@Valid DataAnswerUpdate dataAnswerUpdate) {
        if(dataAnswerUpdate.idAnswer() == null){
            throw new IntegrityValidation("Ha ocurrido un problema con los parametros enviados...");
        }

        if(!answerRepository.existsById(dataAnswerUpdate.idAnswer())){
            throw new IntegrityValidation("La respuesta a actualizar no existe...");
        }

        Answer answerUpdated = answerRepository.getReferenceById(dataAnswerUpdate.idAnswer());

        generalValidations.forEach(gv -> gv.validateGeneral(answerUpdated));
        answerValidations.forEach(av -> av.validateAnswer(answerUpdated));
        answerValidations.forEach(av -> av.validateAnswer(dataAnswerUpdate));

        answerUpdated.updateAnswer(dataAnswerUpdate);

        return answerUpdated;
    }
}
