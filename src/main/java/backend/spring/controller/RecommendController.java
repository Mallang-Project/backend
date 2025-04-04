package backend.spring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.dto.request.RecommendRequest;
import backend.spring.dto.response.RecommendMovieResponseDto;
import backend.spring.service.RecommendService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {

	private final RecommendService recommendService;

	@PostMapping("/{visitorId}")
	public ResponseEntity<List<RecommendMovieResponseDto>> recommendMovies(
		@PathVariable Long visitorId,
		@RequestBody RecommendRequest request) {

		List<RecommendMovieResponseDto> response = recommendService.recommendMovies(visitorId, request);
		return ResponseEntity.ok(response);
	}
}
