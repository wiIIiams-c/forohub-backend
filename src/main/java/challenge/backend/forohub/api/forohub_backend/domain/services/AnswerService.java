package challenge.backend.forohub.api.forohub_backend.domain.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.backend.forohub.api.forohub_backend.domain.answer.Answer;
import challenge.backend.forohub.api.forohub_backend.domain.answer.AnswerRepository;
import challenge.backend.forohub.api.forohub_backend.domain.answer.DataAnswer;
import challenge.backend.forohub.api.forohub_backend.domain.answer.DataSavedAnswer;
import challenge.backend.forohub.api.forohub_backend.domain.topic.Topic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.TopicRepository;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserEntity;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserRepository;
import challenge.backend.forohub.api.forohub_backend.domain.validations.AnswerValidations;
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

    @Autowired
    public AnswerService(TopicRepository topicRepository, AnswerRepository answerRepository, UserRepository userRepository, 
                            List<AnswerValidations> answerValidations, List<TopicValidations> topicValidations) {
        this.topicRepository = topicRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.answerValidations = answerValidations;
        this.topicValidations = topicValidations;
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
}
