package backend.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Getter;

@Configuration
@Getter
public class OpenAiConfig {
	@Value("${openai.api-key}")
	private String apiKey;

	@Value("${openai.api-url}")
	private String apiUrl;

	@Bean
	public WebClient openAiWebClient() { //gpt api 헤더 설정
		return WebClient.builder()
			.baseUrl(apiUrl)
			.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}
}
