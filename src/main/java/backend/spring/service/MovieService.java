package backend.spring.service;

import org.springframework.stereotype.Service;

import backend.spring.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieService {
	private final MovieRepository movieRepository;
}
