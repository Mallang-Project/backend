package backend.spring.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import lombok.Getter;

@Getter
public class ChatResponseDto extends ResponseDto {
	private final String message;

	private ChatResponseDto(String message) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.message = message;
	}

	public static ResponseEntity<ChatResponseDto> success(String message) {
		ChatResponseDto result = new ChatResponseDto(message);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<ResponseDto> invalid_format() {
		ResponseDto result = new ResponseDto(ResponseCode.INVALID_FORMAT, ResponseMessage.INVALID_FORMAT);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	public static ResponseEntity<ResponseDto> openai_limit() {
		ResponseDto result = new ResponseDto(ResponseCode.OPENAI_LIMIT, ResponseMessage.OPENAI_LIMIT);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

}
