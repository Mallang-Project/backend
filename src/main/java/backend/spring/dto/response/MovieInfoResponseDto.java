package backend.spring.dto.response;

public record MovieInfoResponseDto(
	String title,
	String hour,
	String year,
	String image,
	String genre,
	String summary,
	Double score,
	String director,
	String actor1,
	String actor2,
	String origin
) {
}
