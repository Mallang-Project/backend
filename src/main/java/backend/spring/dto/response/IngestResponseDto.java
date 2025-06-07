package backend.spring.dto.response;

public record IngestResponseDto(
        int success,
        int fail
) {
}
