package backend.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.dto.request.ReviewRequestDto;
import backend.spring.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping("/submit/{visitorId}")
	public ResponseEntity createReview(
		@PathVariable Long visitorId,
		@RequestBody @Valid ReviewRequestDto requestDto) {
		reviewService.submitReview(visitorId, requestDto);
		return ResponseEntity.status(201).build();
	}

}
