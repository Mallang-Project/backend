package backend.spring.service;

import org.springframework.stereotype.Service;

import backend.spring.dto.response.MovieInfoResponseDto;
import backend.spring.entity.Movie;
import backend.spring.exception.CustomException;
import backend.spring.exception.ResponseCode;
import backend.spring.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieInfoService {

	private final MovieRepository movieRepository;

	private final String imageBaseUrl = "https://mallang.info/images/";

	public MovieInfoResponseDto getMovieIntfo(Long movieId) {
		Movie movie = movieRepository.findById(movieId)
			.orElseThrow(() -> new CustomException(ResponseCode.MOVIE_NOT_FOUND));

		String fullImageUrl = imageBaseUrl + movie.getImage();

		return new MovieInfoResponseDto(
			movie.getTitle(),
			movie.getHour(),
			movie.getYear(),
			fullImageUrl,
			movie.getGenre(),
			movie.getSummary(),
			movie.getScore(),
			movie.getDirector(),
			movie.getActor1(),
			movie.getActor2(),
			movie.getOrigin()
		);
	}
}
