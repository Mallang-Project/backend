package backend.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import backend.spring.dto.request.ChatRequestDto;
import backend.spring.dto.response.ChatResponseDto;
import backend.spring.exception.CustomException;
import backend.spring.exception.ResponseCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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

	@Transactional
	public Mono<ChatResponseDto> getChatCompletionAsync(ChatRequestDto request) {

		String emotion = request.emotion();
		String style = request.style();
		String tone = request.tone();

		//db에 해당 조합이 존재하는지 확인
		Optional<TodayWord> todayWord = todayWordRepository.findByEmotionAndStyleAndTone(emotion, style, tone);
		if (todayWord.isPresent()) {
			String word = todayWord.get().getWord();
			return Mono.just(new ChatResponseDto(word));
		}

		// 0. 사용자 태그 가져와 스크립트에 추가
		String script =
				"너는 심리 상담 전문가이며, 감정적으로 지친 사람들에게 공감 어린 말과 위로를 해주는 역할을 하고 있어. "
				+ "말투는 따뜻하고 조용하며, 강요하지 않도록 유의해. 문장은 하나의 단락으로 이어서 써야 하며, 절대 줄바꿈을 하지 마. '\\n' 문자도 쓰지 마. "
				+ "결과는 하나의 자연스러운 문장 흐름으로, 문단이 끊기지 않도록 써줘. 항목을 구분하지 말고 연결해서 문단 하나로 써줘. "
				+ "예시: '지루한 하루 속에서도 당신이 새로운 기분 전환을 시도하려는 모습이 참 멋져요. 이런 태도는 일상에 작은 활력을 불어넣을 수 있어요. 오늘 하루도 당신은 충분히 잘해내고 있어요.' 이런 식으로, 구조가 드러나지 않도록 써줘."
				+ "\n"
				+ "사용자 정보:\n"
				+ "- 감정 상태: " + emotion + "\n"
				+ "- 감정 해소 방식: " + style + "\n"
				+ "- 듣고 싶은 말의 톤: " + tone + "\n"
				+ "\n"
				+ "이 정보를 바탕으로 한 문단의 감성적인 문장을 작성해주세요. 문장은 하나로 이어지며, 영화에 대한 언급도 자연스럽게 포함시켜 주세요. 500자 이내로 써주세요.";

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
			.flatMap(response -> { //gpt에게 응답(response) 받아 저장
				Map choice = (Map)((List)response.get("choices")).get(0);
				Map message = (Map)choice.get("message");
				String content = (String)message.get("content");

				todayWordRepository.save(TodayWord.of(emotion, style, tone, content));
				return Mono.just(new ChatResponseDto(content));
			})
			.onErrorResume(WebClientResponseException.TooManyRequests.class, e -> {
				return Mono.error(new CustomException(ResponseCode.OPENAI_LIMIT)); //gpt 제한 초과
			})
			.onErrorResume(WebClientResponseException.class, e -> {
				return Mono.error(new CustomException(ResponseCode.INVALID_FORMAT)); // 나머지 모든 4xx, 5xx 오류: 일반적인 GPT 오류 처리
			});
	}
}
