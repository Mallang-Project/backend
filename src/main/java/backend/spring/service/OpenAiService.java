package backend.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import backend.spring.dto.request.ChatRequestDto;
import backend.spring.dto.response.ChatResponseDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.entity.TodayWord;
import backend.spring.repository.TodayWordRepository;
import reactor.core.publisher.Mono;

@Service
public class OpenAiService {

	private final WebClient webClient;
	private final TodayWordRepository todayWordRepository;

	//service 클래스 생성자
	public OpenAiService(@Qualifier("openAiWebClient") WebClient webClient, TodayWordRepository todayWordRepository) {
		this.webClient = webClient;
		this.todayWordRepository = todayWordRepository;
	}

	public Mono<ResponseEntity<? extends ResponseDto>> getChatCompletionAsync(ChatRequestDto request) {
		// 프론트에서 메시지 누락 or null이면 오류
		String emotion = request.getEmotion();
		String style = request.getStyle();
		String tone = request.getTone();
		if (!StringUtils.hasText(emotion) || !StringUtils.hasText(style) || !StringUtils.hasText(tone)) {
			return Mono.just(ChatResponseDto.invalid_format());
		} //Mono를 씌워서 리턴함

		try{
			//존재하는지 확인
			Optional<TodayWord> todayWord = todayWordRepository.findByEmotionAndStyleAndTone(emotion, style, tone);
			if (todayWord.isPresent()) {
				String word = todayWord.get().getWord();
				return Mono.just(ChatResponseDto.success(word));
			}
		} catch (Exception e){
			e.printStackTrace();
			return Mono.just(ResponseDto.databaseError());
		}

		// 0. 사용자 태그 가져와 스크립트에 추가
		String script = "당신은 감정 기반 영화 추천 사이트에서 감정 분석 결과를 요약해주는 작가입니다.\n"
			+ "\n"
			+ "사용자 정보:\n"
			+ "\n"
			+ "- 현재 감정: "+emotion+"\n"
			+ "- 감정 해소 방식: "+style+"\n"
			+ "- 듣고 싶은 말의 톤: 위로"+tone+"\n"
			+ "\n"
			+ "다음 조건에 따라 결과지를 작성해주세요:\n"
			+ "\n"
			+ "[결과지 구성]\n"
			+ "\n"
			+ "1. 감정 요약 (현재 감정에 공감해주는 문장 1~2줄)\n"
			+ "2. 사용자 성향 피드백 (해소 방식에 대한 긍정적 리액션)\n"
			+ "3. 영화 추천 방향 안내 (어떤 스타일의 영화를 보면 좋을지 한 줄)\n"
			+ "4. 한 마디 응원 (사용자 톤에 맞는 문장 1줄)\n"
			+ "\n"
			+ "형식은 부드럽고 친근한 말투로 작성해주세요.\n"
			+ "\n"
			+ "전체 길이는 300자 이내로 제한해주세요.";

		// 1. 메시지 리스트 구성
		List<Map<String, String>> messages = new ArrayList<>();
		// system 메시지
		Map<String, String> systemMsg = Map.of(
			"role", "system",
			"content", script
		);
		// user 메시지 (요청 트리거 역할)
		Map<String, String> userMsg = Map.of(
			"role", "user",
			"content", "결과지를 작성해줘"
		);
		messages.add(systemMsg);
		messages.add(userMsg);

		// 2. 최종 요청 payload 구성
		Map<String, Object> requestPayload = Map.of(
			"model", "gpt-3.5-turbo",
			"temperature", 0.7,
			"messages", messages
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
