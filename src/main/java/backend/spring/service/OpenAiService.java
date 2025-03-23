package backend.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import backend.spring.dto.object.Message;
import backend.spring.dto.request.ChatRequestDto;
import backend.spring.dto.response.ChatResponseDto;
import backend.spring.dto.response.ResponseDto;
import reactor.core.publisher.Mono;

@Service
public class OpenAiService {

	private final WebClient webClient;

	public OpenAiService(@Qualifier("openAiWebClient") WebClient webClient) {
		this.webClient = webClient;
	}

	public Mono<ResponseEntity<? extends ResponseDto>> getChatCompletionAsync(ChatRequestDto chatRequest) {
		// 프론트에서 메시지 누락 or null이면 오류
		if (chatRequest.getMessages() == null) {
			return Mono.just(ChatResponseDto.invalid_format());
		} //Mono를 씌워서 리턴함

		// 0. 사용자 태그 가져와 스크립트에 추가
		String script = "너는 심리상담사야. 사용자는 현재 스트레스 상태야. 이 사용자를 위해 공감하고 따뜻하게 심리상담을 해줘.";

		// 1. system 메시지 정의
		Message systemMessage = new Message("system", script);
		// 2. messages 리스트 구성 (시스템 메세지 + 프론트에게 받은 메세지)
		List<Message> allMessages = new ArrayList<>();
		allMessages.add(systemMessage);
		allMessages.addAll(chatRequest.getMessages());
		// 3. 최종 gpt에게 보낼 Payload 구성
		Map<String, Object> requestPayload = Map.of(
			"model", "gpt-3.5-turbo",
			"temperature", 0.7,
			"messages", allMessages
		);

		return webClient.post()  //gpt api에 post 요청
			.bodyValue(requestPayload) //model+message 같이 보냄
			.retrieve()  // 응답 받기 준비
			.bodyToMono(Map.class) //응답(json)을 Mono<Map>으로 변환
			.map(response -> {
				try {
					Map choice = (Map)((List)response.get("choices")).get(0);
					Map message = (Map)choice.get("message");
					String content = (String)message.get("content");
					return ChatResponseDto.success(content);
				} catch (Exception e) {
					return ChatResponseDto.invalid_format();
				}
			})
			.onErrorResume(WebClientResponseException.TooManyRequests.class, e -> {
				return Mono.just(ChatResponseDto.openai_limit()); //gpt 제한 초과
			})
			.onErrorResume(WebClientResponseException.class, e -> {
				return Mono.just(ChatResponseDto.invalid_format());
			})
			.onErrorResume(Exception.class, e -> { //기타 예상하지 못한 모든 예외 처리
				return Mono.just(ResponseDto.databaseError());
			}); //NullPointerException, JSON 구조 변경 등
	}
}
