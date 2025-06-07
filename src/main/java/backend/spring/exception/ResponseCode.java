package backend.spring.exception;

import lombok.Getter;

@Getter
public enum ResponseCode {
	USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
	MOVIE_NOT_FOUND(404, "영화를 찾을 수 없습니다."),
	NO_RECOMMENDATION_FOUND(404, "조건에 맞는 영화가 없습니다."),
	INVALID_FORMAT(400, "잘못된 입력 형식 입니다."),
	OPENAI_LIMIT(400, "gpt api의 사용량을 초과했습니다."),
	INVALID_ENUM_FORMAT(400, "잘못된 입력 형식입니다."),
	INVALID_HTML_FORMAT(400, "HTML 내용이 비어 있습니다."),
	FILE_SAVE_ERROR(500, "HTML 파일 저장 중 오류가 발생했습니다.");

	private final int status;
	private final String message;

	ResponseCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
