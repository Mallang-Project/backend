package backend.spring.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChatRequestDto(
        @NotBlank String emotion,
        @NotBlank String style,
        @NotBlank String tone
) {
}
