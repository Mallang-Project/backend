package backend.spring.service;

import backend.spring.dto.request.ChatRequestDto;
import backend.spring.dto.response.ChatResponseDto;
import backend.spring.service.client.OpenAiClient;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import backend.spring.entity.TodayWord;
import backend.spring.repository.TodayWordRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OpenAiService {

	private final OpenAiClient gpt;
	private final TodayWordRepository todayWordRepository;

	@Transactional
	public Mono<ChatResponseDto> getChatCompletionAsync(ChatRequestDto request) {

		String emotion = request.emotion();
		String style = request.style();
		String tone = request.tone();

		return Mono.fromCallable(() -> todayWordRepository.findByKeys(emotion, style, tone)) //db 조회
				.subscribeOn(Schedulers.boundedElastic())        // JPA 블로킹 → 전용 스레드
				.flatMap(opt -> opt                             // 존재하면 즉시 반환
						.<Mono<ChatResponseDto>>map(tw -> Mono.just(new ChatResponseDto(tw.getWord())))
						.orElseGet(() -> gpt.gptMessage(emotion, style, tone)   /* ② GPT 호출 + DB 저장 */
										.flatMap(message ->
												Mono.fromCallable( () -> todayWordRepository.save(TodayWord.of(emotion, style, tone, message)) )
														.subscribeOn(Schedulers.boundedElastic())   // 저장도 블로킹
														.thenReturn(message)                        // message 그대로 방출
										)
										.map(ChatResponseDto::new)                      // String → DTO
						)
				);
	}
}
