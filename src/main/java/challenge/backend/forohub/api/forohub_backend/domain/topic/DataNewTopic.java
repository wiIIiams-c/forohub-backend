package challenge.backend.forohub.api.forohub_backend.domain.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataNewTopic(
    @NotBlank String title,
    @NotBlank String message,
    @NotNull Long author,
    @NotNull Long course
) {

}
