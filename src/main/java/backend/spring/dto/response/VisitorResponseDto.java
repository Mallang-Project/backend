package backend.spring.dto.response;

public record VisitorResponseDto(
	Long visitorId,
	String nickname,
	boolean isAdminViewable) {
}
