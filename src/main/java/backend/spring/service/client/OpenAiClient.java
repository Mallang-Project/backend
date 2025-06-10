package backend.spring.service.client;

import backend.spring.dto.object.MovieData;
import backend.spring.dto.object.TmdbData;
import backend.spring.exception.CustomException;
import backend.spring.exception.ResponseCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Component
public class OpenAiClient {

    private final WebClient gptWebClient;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public OpenAiClient(@Qualifier("openAiWebClient") WebClient gptwebClient) {
        this.gptWebClient = gptwebClient;
    }

    /* ────────────────── 공통 예외 ────────────────── */
    private RuntimeException mapOpenAiException(WebClientResponseException ex) {
        return switch (ex.getStatusCode()) {
            case TOO_MANY_REQUESTS -> new CustomException(ResponseCode.OPENAI_LIMIT);
            case BAD_REQUEST       -> new CustomException(ResponseCode.INVALID_FORMAT);
            default                -> new CustomException(ResponseCode.INVALID_FORMAT);
        };
    }

    /* ────────────────── 영화 번역 + 감정 태그 ────────────────── */
    public Mono<MovieData> translateAndTag(TmdbData src) {
        Map<String, Object> payload = movieBuildPrompt(src);

        return gptWebClient.post()
                .bodyValue(payload)
                .exchangeToMono(this::handleMovieResponse)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)));
    }
    /** 영화 번역·감정 태그용: OpenAI JSON → MovieData */
    private Mono<MovieData> handleMovieResponse(ClientResponse res) {
        if (res.statusCode().is2xxSuccessful()) {
            return res.bodyToMono(Map.class)          // OpenAI 원본 JSON
                    .map(this::parseResult);   // ➜ MovieData
        }
        return res.createException()
                .flatMap(ex -> Mono.error(mapOpenAiException(ex)));
    }
    @SuppressWarnings("unchecked")
    private MovieData parseResult(Map<?, ?> rawJson) {
        try {
            // 1. choices → [ { message: { content: "{...}" } } ]
            List<Map<String, Object>> choices = (List<Map<String, Object>>) rawJson.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");

            // 2. content는 문자열 형태의 JSON → 다시 파싱
            String jsonString = (String) message.get("content");
            Map<String, Object> content = MAPPER.readValue(jsonString, new TypeReference<>() {});

            // 3. MovieData 생성 (GPT에서 받은 필드만 사용)
            return new MovieData(
                    (String) content.get("genre"),
                    (String) content.get("title"),
                    (String) content.get("summary"),
                    (String) content.get("year"),
                    (String) content.get("image"),
                    (String) content.get("score"),
                    (String) content.get("hour"),
                    (String) content.get("director"),
                    (String) content.get("actor1"),
                    (String) content.get("actor2"),
                    (String) content.get("emotion"),
                    (String) content.get("style")
            );
        } catch (Exception e) {
            throw new IllegalStateException("GPT 응답 파싱 실패", e);
        }
    }
    // 스크립트 build
    public Map<String, Object> movieBuildPrompt(TmdbData src) {
        try{
            // 0. 시스템 프롬프트
            String script = """
                당신은 영화 분석 전문가입니다. 다음 JSON 객체를 분석하고 아래 지시를 따르세요:
                
                1. "title", "summary", "director", "actor1", "actor2" 값을 영어에서 한국어로 번역하세요.
                2. 관객이 이 영화를 보며 가장 느낄 감정(emotion)을 정확히 하나 선택하세요.  
                   선택지: 슬픔, 행복, 지루함, 스트레스
                3. 해당 감정을 해소하거나 조화시키는 방식(style)을 정확히 하나 선택하세요.  
                   선택지: 몰입감, 웃긴, 기분전환, 위로
                4. 번역된 값과 선택한 감정 정보(emotion, style)를 포함한 **단일 JSON 객체**를 반환하세요.
                   반드시 원시 JSON만 출력하세요. 줄바꿈 없이.
                
                예시 출력:
                {
                  "genre": "드라마",
                  "title": "스파이더맨: 홈커밍",
                  "summary": "캡틴 아메리카: 시빌 워 사건 이후, 퀸스의 평범한 고등학생 피터 파커는 멘토인 토니 스타크의 도움을 받으며 영웅 ‘스파이더맨’과 학생 생활을 병행하려고 애쓴다. 그러던 중 새로운 악당 벌처가 등장하자 피터는 도시에 닥친 위협에 맞서야 한다.",
                  "year": "2017-07-05",
                  "image": "https://image.tmdb.org/t/p/w500/c24sv2weTHPsmDa7jEMN0m2P3RT.jpg",
                  "score": "7.3",
                  "hour": "133",
                  "director": "존 왓츠",
                  "actor1": "톰 홀랜드",
                  "actor2": "마이클 키튼",
                  "emotion": "행복",
                  "style": "몰입감"
                }
                """;

            // 1. 메시지 구성
            Map<String, String> systemMsg = Map.of("role", "system", "content", script);

            Map<String, Object> movieMap = new LinkedHashMap<>();
            movieMap.put("genre",    src.genre());
            movieMap.put("title",    src.title());
            movieMap.put("summary",  src.summary());
            movieMap.put("year",     src.year());
            movieMap.put("image",    src.image());
            movieMap.put("score",    src.score());
            movieMap.put("hour",     src.hour());
            movieMap.put("director", src.director());
            movieMap.put("actor1",   src.actor1());
            movieMap.put("actor2",   src.actor2());

            String userJson = MAPPER.writeValueAsString(movieMap);

            return Map.of(
                    "model",           "gpt-3.5-turbo-0125",
                    "temperature",     0.0,
                    "response_format", Map.of("type", "json_object"),
                    "messages",        List.of(systemMsg, Map.of("role","user","content",userJson))
            );
        } catch (JsonProcessingException e) {
            // 직렬화 실패 → 논리적으로 잘못된 입력이므로 런타임 예외로 래핑
            throw new IllegalStateException("영화 정보를 JSON 문자열로 변환할 수 없습니다.", e);
        }
    }


    /* ────────────────── Today-Word 메시지 ────────────────── */
    public Mono<String> gptMessage(String emotion, String style, String tone) {
        Map<String, Object> payload = wordBuildPrompt(emotion, style, tone);

        return gptWebClient.post()
                .bodyValue(payload)
                .exchangeToMono(this::handleWordResponse);
    }
    /** Today-Word 용: OpenAI JSON → String */
    private Mono<String> handleWordResponse(ClientResponse res) {
        if (res.statusCode().is2xxSuccessful()) {
            return res.bodyToMono(Map.class)
                    .map(json -> {
                        Map<?, ?> choice  = (Map<?, ?>) ((List<?>) json.get("choices")).getFirst();
                        Map<?, ?> message = (Map<?, ?>) choice.get("message");
                        return (String) message.get("content");   // ← 바로 추출
                    });
        }
        return res.createException()
                .flatMap(ex -> Mono.error(mapOpenAiException(ex)));
    }
    public Map<String, Object> wordBuildPrompt(String emotion, String style, String tone){
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
                        + "이 정보를 바탕으로 한 문단의 감성적인 문장을 작성해주세요. 문장은 하나로 이어지며, 영화에 대한 언급도 자연스럽게 포함시켜 주세요. 300자 이내로 써주세요.";

        // 1. 메시지 구성
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
        Map<String, Object> requestPlayload = Map.of(
                "model", "gpt-3.5-turbo",
                "temperature", 0.7,
                "messages", messages
        );

        return requestPlayload;
    }

}
