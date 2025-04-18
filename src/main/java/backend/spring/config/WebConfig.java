package backend.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")  // 모든 경로에 대해 CORS 허용
					.allowedOrigins("http://localhost:3000")  // 요청을 보낼 origin(프론트엔드) 허용
					.allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드
					.allowedHeaders("*");  // 허용할 헤더
			}
		};
	}
}
