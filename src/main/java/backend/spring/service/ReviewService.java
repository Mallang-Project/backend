package backend.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import backend.spring.dto.request.ReviewRequestDto;
import backend.spring.dto.response.ReviewListResponseDto;
import backend.spring.dto.response.ReviewResponseDto;
import backend.spring.entity.Visitor;
import backend.spring.exception.CustomException;
import backend.spring.exception.ResponseCode;
import backend.spring.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final VisitorRepository visitorRepository;

	public void submitReview(Long visitorId, ReviewRequestDto requestDto) {
		Visitor visitor = visitorRepository.findById(visitorId)
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

		visitor.setReview(requestDto.score(), requestDto.review());
		visitorRepository.save(visitor);
	}

	public ReviewListResponseDto getReviewList(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<Visitor> reviewPage = visitorRepository.findAllByReviewIsNotNull(pageable);

		List<ReviewResponseDto> reviews = reviewPage.getContent()
			.stream()
			.map(visitor -> new ReviewResponseDto(
				visitor.getNickname(),
				visitor.getScore(),
				visitor.getReview(),
				visitor.getCreatedAt().toLocalDate().toString()
			))
			.toList();

		double averageScore = visitorRepository.getAverageScore();

		return new ReviewListResponseDto(
			reviews,
			(int)reviewPage.getTotalElements(),
			averageScore,
			page,
			size
		);
	}
}
