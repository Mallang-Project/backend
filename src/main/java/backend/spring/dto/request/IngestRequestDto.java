package backend.spring.dto.request;

public record IngestRequestDto(
        String genreId,
        int page
) {
}
