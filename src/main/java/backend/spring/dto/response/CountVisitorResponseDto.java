package backend.spring.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.spring.common.ResponseCode;
import backend.spring.common.ResponseMessage;
import lombok.Getter;

@Getter
public class CountVisitorResponseDto extends ResponseDto {
	int totalCount;
	int todayCount;

	public CountVisitorResponseDto(int totalCount, int todayCount) {
		super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
		this.totalCount = totalCount;
		this.todayCount = todayCount;
	}

	public static ResponseEntity<CountVisitorResponseDto> success(int totalCount, int todayCount){
		CountVisitorResponseDto result = new CountVisitorResponseDto(totalCount, todayCount);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
