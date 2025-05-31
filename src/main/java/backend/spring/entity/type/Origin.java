package backend.spring.entity.type;

import static backend.spring.exception.ResponseCode.*;

import java.util.Arrays;

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

	public static Origin from(String name) {
		return Arrays.stream(values())
			.filter(e -> e.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new CustomException(INVALID_ENUM_FORMAT));
	}
}
