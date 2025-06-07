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
public enum Genre {
	ROMANCE("로맨스"),
	COMEDY("코미디"),
	ANIMATION("애니메이션"),
	DRAMA("드라마"),
	ACTION("액션"),
	THRILLER("스릴러");

	private final String name;

	@JsonCreator
	public static Genre from(String name) {
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
