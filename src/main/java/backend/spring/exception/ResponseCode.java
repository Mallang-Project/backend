package backend.spring.exception;

import lombok.Getter;

@Getter
public enum ResponseCode {
	USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다.");

	private final int status;
	private final String message;

	ResponseCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
