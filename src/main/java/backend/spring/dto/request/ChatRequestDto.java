package backend.spring.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ChatRequestDto {
	private String emotion;
	private String style;
	private String tone;
}
