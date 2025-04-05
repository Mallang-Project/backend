package backend.spring.dto.response;

public record ReviewResponseDto(
	String nickname,
	int score,
	String review,
	String createdDate
) {
}
