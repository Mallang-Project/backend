package backend.spring.entity.type;

import static backend.spring.exception.ResponseCode.*;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import backend.spring.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Origin {
	KOREA("한국"),
	EAST_ASIA("동아시아"),
	WEST("서구"),
	OTHER("기타");

	private final String name;

	@JsonCreator
	public static Origin from(String name) {
		return Arrays.stream(values())
			.filter(e -> e.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new CustomException(INVALID_ENUM_FORMAT));
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
