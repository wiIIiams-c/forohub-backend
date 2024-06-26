package challenge.backend.forohub.api.forohub_backend.domain.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import challenge.backend.forohub.api.forohub_backend.domain.answer.DataAnswer;
import challenge.backend.forohub.api.forohub_backend.domain.topic.TopicRepository;
import challenge.backend.forohub.api.forohub_backend.infra.errors.IntegrityValidation;

@Component
public class TopicClosed implements TopicValidations{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validateTopic(Object data) {
        Long idTopic = null;

        if(data instanceof Long){
            idTopic = (Long) data;
        }else if(data instanceof DataAnswer){
            idTopic = ((DataAnswer) data).topic();
        }

        if(idTopic != null){
            var topic = topicRepository.getReferenceById(idTopic);

            if(!topic.getStatus().booleanValue()){
                throw new IntegrityValidation("El topic se encuentra cerrado...");
            }
        }else{
            return;
        }
    }

}
