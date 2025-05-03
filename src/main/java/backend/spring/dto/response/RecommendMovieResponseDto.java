package backend.spring.dto.response;

public record RecommendMovieResponseDto(
	Long movieId,
	String title,
	String hour,
	String year,
	String image
) {
}
