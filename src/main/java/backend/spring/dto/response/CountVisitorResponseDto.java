package backend.spring.dto.response;

public record CountVisitorResponseDto(
        int totalCount,
        int todayCount
) {
}
