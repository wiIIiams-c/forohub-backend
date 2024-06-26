package challenge.backend.forohub.api.forohub_backend.domain.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataAnswer(
    @NotNull Long author,
    @NotNull Long topic,
    @NotBlank String message
) {

}
