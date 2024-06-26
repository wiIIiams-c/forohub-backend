package challenge.backend.forohub.api.forohub_backend.domain.answer;

import java.time.LocalDateTime;

public record DataSavedAnswer(
    Long idAnswer,
    String message,
    String topicTitle,
    LocalDateTime creationDate
) {
    public DataSavedAnswer(Answer answer){
        this(
            answer.getId(),
            answer.getAnswer(),
            answer.getTopic().getTitle(),
            answer.getCreationDate()
        );
    }
}
