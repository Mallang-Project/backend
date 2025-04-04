package backend.spring.dto.response;

import java.util.List;

public record ReviewListResponseDto(
	List<ReviewResponseDto> reviews,
	int totalCount,
	double averageScore,
	int page,
	int size
) {
}
