package challenge.backend.forohub.api.forohub_backend.domain.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import challenge.backend.forohub.api.forohub_backend.domain.course.Course;
import challenge.backend.forohub.api.forohub_backend.domain.course.CourseRepository;
import challenge.backend.forohub.api.forohub_backend.domain.topic.DataTopic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.DataTopicList;
import challenge.backend.forohub.api.forohub_backend.domain.topic.DataSavedTopic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.Topic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.TopicRepository;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserEntity;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserRepository;
import challenge.backend.forohub.api.forohub_backend.domain.validations.TopicValidations;
import challenge.backend.forohub.api.forohub_backend.infra.errors.IntegrityValidation;
import jakarta.validation.Valid;

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

    public DataSavedTopic addNewTopic(DataTopic dataNewTopic) {
        //que el curso y el usuario exista
        var methodCourse = getSelectedCourse(dataNewTopic);
        var methodUser = getSelectedUser(dataNewTopic);
        
        //que los titulos y mensajes no sean iguales
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

    public Topic updateTopic(Long id, @Valid DataTopic dataUpdateTopic) {
        //que no venga nada null -> ya lo valida en el record DataTopic
        //exista el id
        if(!topicRepository.findById(id).isPresent()){
            throw new IntegrityValidation("El topic a actualizar no ha sido encontrado...");
        }
        
        //que el id del autor sea el mismo del topic via sql
        if(!topicRepository.findByIdAndAuthorId(id, dataUpdateTopic.author()).isPresent()){
            throw new IntegrityValidation("El autor no corresponde al topic a actualizar...");
        }

        //que los titulos y mensajes no sean iguales
        topicValidations.forEach(tv -> tv.validateTopic(dataUpdateTopic));

        //que el curso y el usuario exista
        var methodCourse = getSelectedCourse(dataUpdateTopic);
        var methodUser = getSelectedUser(dataUpdateTopic);

        //actualizar
        Topic updateTopic = topicRepository.getReferenceById(id);
        updateTopic.updateTopic(dataUpdateTopic, methodCourse);

        //retorna datos actualizados, excepto fecha de creacion
        return updateTopic;
    }

    public String deleteTopic(Long id){
        var topicToDelete = topicRepository.findById(id);

        //exista el id
        if(!topicToDelete.isPresent()){
            throw new IntegrityValidation("El topic a eliminar no ha sido encontrado...");
        }

        //valida que el autor sea el mismo del topic a eliminar via custom validation
        topicValidations.forEach(tv -> tv.validateTopic(topicToDelete.get().getAuthor().getId()));

        //eliminar topic
        topicRepository.deleteById(id);

        //mensaje de que ha sido eliminado
        return "El topic ha sido eliminado...";
    }

    public Page<DataTopicList> listTopic(Pageable pageable){
        return topicRepository.findAll(pageable).map(DataTopicList::new);
    }

    public DataTopicList detailTopic(@Valid Long id) {
        //exista el id
        if(!topicRepository.findById(id).isPresent()){
            throw new IntegrityValidation("El topic a consultar no ha sido encontrado...");
        }

        return new DataTopicList(topicRepository.getReferenceById(id));
    }

    private Course getSelectedCourse(DataTopic data){
        if(!courseRepository.findById(data.course()).isPresent()){
            throw new IntegrityValidation("El curso ingresado es invalido...");
        }

        return courseRepository.getReferenceById(data.course());
    }

    private UserEntity getSelectedUser(DataTopic data){
        if(!userRepository.findById(data.author()).isPresent()){
            throw new IntegrityValidation("El usuario ingresado es invalido...");
        }

        return userRepository.getReferenceById(data.author());
    }
}
