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
public enum Style {
	REFRESHING("기분전환"),
	COMFORTING("위로"),
	IMMERSIVE("몰입감"),
	FUNNY("웃긴");

	private final String name;

	@JsonCreator
	public static Style from(String name) {
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

