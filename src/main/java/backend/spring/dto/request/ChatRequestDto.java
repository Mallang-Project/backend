package backend.spring.dto.request;

import java.util.List;

import backend.spring.dto.object.Message;
import lombok.Data;

@Data
public class ChatRequestDto {
	private List<Message> messages;
}
