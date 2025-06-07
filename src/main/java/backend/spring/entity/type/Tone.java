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
public enum Tone {
	COMFORT("위로"),
	ADVICE("조언"),
	MOTIVATION("동기부여"),
	CHEER("응원");

	private final String name;

	@JsonCreator
	public static Tone from(String name) {
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
