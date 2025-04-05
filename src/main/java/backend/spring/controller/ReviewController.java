package backend.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.dto.request.ReviewRequestDto;
import backend.spring.dto.response.ReviewListResponseDto;
import backend.spring.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping("/submit/{visitorId}")
	public ResponseEntity<Void> createReview(
		@PathVariable Long visitorId,
		@RequestBody @Valid ReviewRequestDto requestDto) {
		reviewService.submitReview(visitorId, requestDto);
		return ResponseEntity.status(201).build();
	}

	@GetMapping("/list")
	public ResponseEntity<ReviewListResponseDto> getReviewList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		ReviewListResponseDto responseDto = reviewService.getReviewList(page, size);
		return ResponseEntity.ok(responseDto);
	}
}
