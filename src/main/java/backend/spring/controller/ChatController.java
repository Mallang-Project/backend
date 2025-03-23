package backend.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.dto.request.ChatRequestDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatController {

	private final OpenAiService openAiService;

	@PostMapping("/message")
	public Mono<ResponseEntity<? extends ResponseDto>> chat(@RequestBody ChatRequestDto request) {
		return openAiService.getChatCompletionAsync(request);
	}
}