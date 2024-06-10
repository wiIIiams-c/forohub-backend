package challenge.backend.forohub.api.forohub_backend.domain.topic;

import java.time.LocalDateTime;
import java.util.Set;

import challenge.backend.forohub.api.forohub_backend.domain.answer.Answer;
import challenge.backend.forohub.api.forohub_backend.domain.course.Course;
import challenge.backend.forohub.api.forohub_backend.domain.user.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "topics")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Column(unique = true)
    private String message;
    private LocalDateTime creationDate;
    private Boolean status;
    
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private UserEntity author;
    
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Answer> answers;
}
