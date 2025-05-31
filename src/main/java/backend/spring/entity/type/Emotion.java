package backend.spring.entity.type;

import static backend.spring.exception.ResponseCode.*;

import java.util.Arrays;

import backend.spring.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Emotion {
	SAD("슬픔"),
	HAPPY("행복"),
	BORED("지루함"),
	STRESSED("스트레스");

	private final String name;

	public static Emotion from(String name) {
		return Arrays.stream(values())
			.filter(e -> e.name.equals(name))
			.findFirst()
			.orElseThrow(() -> new CustomException(INVALID_ENUM_FORMAT));
	}
}
