package backend.spring.controller;

import backend.spring.dto.request.ChatRequestDto;
import backend.spring.dto.response.ChatResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/mental")
@RequiredArgsConstructor
public class ChatController {

	private final OpenAiService openAiService;

	@PostMapping("/message")
	public ResponseEntity<Mono<ChatResponseDto>> chat(@RequestBody ChatRequestDto request) {
		Mono<ChatResponseDto> result = openAiService.getChatCompletionAsync(request);
		return ResponseEntity.ok(result);
	}
}
