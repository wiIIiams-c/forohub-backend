package challenge.backend.forohub.api.forohub_backend.domain.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import challenge.backend.forohub.api.forohub_backend.domain.topic.DataNewTopic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.TopicRepository;
import jakarta.validation.ValidationException;

@Component
public class TopicExistance implements TopicValidations{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validateTopic(Object data) {
        if(data instanceof DataNewTopic){
            DataNewTopic dnt = (DataNewTopic) data;

            if(dnt.title() == null || dnt.message() == null || dnt.author() == null || dnt.course() == null){
                return;
            }

            var topicTitle = topicRepository.findByTitle(dnt.title().replace(" ", ""));
            var topicMessage = topicRepository.findByMessage(dnt.message().replace(" ", ""));

            if(topicTitle != null){
                throw new ValidationException("El titulo ingresado ya existe...");
            }

            if(topicMessage != null){
                throw new ValidationException("El mensaje ingresado ya existe...");
            }
        }
    }
}
