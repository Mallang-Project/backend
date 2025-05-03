package backend.spring.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import backend.spring.dto.request.RecommendRequest;
import backend.spring.dto.response.RecommendMovieResponseDto;
import backend.spring.entity.Movie;
import backend.spring.entity.Visitor;
import backend.spring.entity.VisitorTag;
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

	private final String imageBaseUrl = "http://3.39.83.134:8080/images/";

	public List<RecommendMovieResponseDto> recommendMovies(Long visitorId, RecommendRequest request) {
		Visitor visitor = visitorRepository.findById(visitorId)
			.orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

		List<Movie> allMovies = movieRepository.findAllByGenreNot(request.hate());

		Set<String> tags = Set.of(
			request.emotion(),
			request.style(),
			request.genre(),
			request.origin()
		);

		Map<Movie, Long> matchCountMap = allMovies.stream()
			.collect(Collectors.toMap(
				movie -> movie,
				movie -> tags.stream()
					.filter(tag -> tag.equals(movie.getEmotion())
						|| tag.equals(movie.getStyle())
						|| tag.equals(movie.getGenre())
						|| tag.equals(movie.getOrigin()))
					.count()
			));

		List<Movie> matchMovies = matchCountMap.entrySet().stream()
			.filter(entry -> entry.getValue() > 0)
			.sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
			.map(Map.Entry::getKey)
			.collect(Collectors.toList());

		if (matchMovies.isEmpty()) {
			throw new CustomException(ResponseCode.NO_RECOMMENDATION_FOUND);
		}

		Collections.shuffle(matchMovies);
		List<Movie> recommended = matchMovies.stream()
			.limit(3)
			.toList();

		VisitorTag tag = new VisitorTag(
			visitor,
			request.emotion(),
			request.style(),
			request.genre(),
			request.origin(),
			request.hate()
		);
		visitorTagRepository.save(tag);


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
