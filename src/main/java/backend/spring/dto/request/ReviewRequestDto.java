package backend.spring.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewRequestDto(
	@NotNull
	@Min(1)
	@Max(5)
	Integer score,

	@NotBlank
	@Size(max = 200)
	String review) {
}
