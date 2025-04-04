package backend.spring.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RecommendRequest(
	@NotBlank String emotion,
	@NotBlank String style,
	@NotBlank String genre,
	@NotBlank String origin,
	@NotBlank String hate
) {
}
