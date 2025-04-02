package backend.spring.service;

import org.springframework.stereotype.Service;

import backend.spring.dto.request.ReviewRequestDto;
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
}
