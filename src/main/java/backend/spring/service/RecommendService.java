package backend.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import backend.spring.dto.request.RecommendRequest;
import backend.spring.dto.response.RecommendMovieResponseDto;
import backend.spring.entity.Movie;
import backend.spring.entity.Visitor;
import backend.spring.entity.VisitorTag;
import backend.spring.entity.type.Emotion;
import backend.spring.entity.type.Genre;
import backend.spring.entity.type.Style;
import backend.spring.exception.CustomException;
import backend.spring.exception.ResponseCode;
import backend.spring.repository.MovieRepository;
import backend.spring.repository.VisitorRepository;
import backend.spring.repository.VisitorTagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendService {

	private final VisitorRepository visitorRepository;
	private final MovieRepository movieRepository;
	private final VisitorTagRepository visitorTagRepository;

	private final String imageBaseUrl = "https://mallang.info/images/";

	public List<RecommendMovieResponseDto> recommendMovies(Long visitorId, RecommendRequest request) {
		Visitor visitor = visitorRepository.findById(visitorId)
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

		Emotion emotion = Emotion.from(request.emotion());
		Style style = Style.from(request.style());
		Genre genre = Genre.from(request.genre());
		Genre hate = Genre.from(request.hate());

		List<Movie> recommended = movieRepository.findRecommendedMovies(
			emotion.name(),
			style.name(),
			genre.name(),
			hate.name()
		);

		if (recommended.isEmpty()) {
			throw new CustomException(ResponseCode.NO_RECOMMENDATION_FOUND);
		}

		visitorTagRepository.save(VisitorTag.of(
			visitor,
			emotion,
			style,
			genre,
			hate
		));

		return recommended.stream()
			.map(movie -> new RecommendMovieResponseDto(
				movie.getId(),
				movie.getTitle(),
				movie.getHour(),
				movie.getYear(),
				imageBaseUrl + movie.getImage()
			))
			.toList();
	}
}
