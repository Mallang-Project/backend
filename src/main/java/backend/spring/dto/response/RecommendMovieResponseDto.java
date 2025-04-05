package backend.spring.dto.response;

public record RecommendMovieResponseDto(
	String title,
	String hour,
	String year,
	String image
) {
}
