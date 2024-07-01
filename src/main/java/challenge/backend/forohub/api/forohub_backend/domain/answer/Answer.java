package challenge.backend.forohub.api.forohub_backend.domain.answer;

import java.time.LocalDateTime;

import challenge.backend.forohub.api.forohub_backend.domain.topic.Topic;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "answers")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;    
    private Boolean solution;

    //Constructor para crear una respuesta
    public Answer(String answer, LocalDateTime creationDate, Topic topic, UserEntity author){
        this.answer = answer;
        this.creationDate = creationDate;
        this.topic = topic;
        this.author = author;
        this.solution = false;
    }

    public void updateAnswer(DataAnswerUpdate dataAnswerUpdate){
        if(dataAnswerUpdate.newMessage() != null){
            this.answer = dataAnswerUpdate.newMessage();
        }
    }

    //metodo para marcar la respuesta como solucionada
    public void markSolution(){
        this.solution = true;
    }
}
