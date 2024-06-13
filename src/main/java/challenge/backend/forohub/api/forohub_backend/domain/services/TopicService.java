package challenge.backend.forohub.api.forohub_backend.domain.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.backend.forohub.api.forohub_backend.domain.course.Course;
import challenge.backend.forohub.api.forohub_backend.domain.course.CourseRepository;
import challenge.backend.forohub.api.forohub_backend.domain.topic.DataNewTopic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.DataSavedTopic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.Topic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.TopicRepository;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserEntity;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserRepository;
import challenge.backend.forohub.api.forohub_backend.domain.validations.TopicValidations;
import challenge.backend.forohub.api.forohub_backend.infra.errors.IntegrityValidation;

@Service
public class TopicService {
    private TopicRepository topicRepository;
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private List<TopicValidations> topicValidations;

    @Autowired
    public TopicService(TopicRepository topicRepository, UserRepository userRepository,
            CourseRepository courseRepository, List<TopicValidations> topicValidations) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.topicValidations = topicValidations;
    }

    public DataSavedTopic addNewTopic(DataNewTopic dataNewTopic) {
        var methodCourse = getSelectedCourse(dataNewTopic);
        var methodUser = getSelectedUser(dataNewTopic);
        
        topicValidations.forEach(tv -> tv.validateTopic(dataNewTopic));

        var currentDate = LocalDateTime.now();        
        var newTopic = new Topic(
            dataNewTopic.title(),
            dataNewTopic.message(),
            currentDate,
            methodUser,
            methodCourse
        );

        topicRepository.save(newTopic);
        
        return new DataSavedTopic(newTopic);
    }

    private Course getSelectedCourse(DataNewTopic data){
        if(!courseRepository.findById(data.course()).isPresent()){
            throw new IntegrityValidation("El curso ingresado es invalido...");
        }

        return courseRepository.getReferenceById(data.course());
    }

    private UserEntity getSelectedUser(DataNewTopic data){
        if(!userRepository.findById(data.author()).isPresent()){
            throw new IntegrityValidation("El usuario ingresado es invalido...");
        }

        return userRepository.getReferenceById(data.author());
    }
}
