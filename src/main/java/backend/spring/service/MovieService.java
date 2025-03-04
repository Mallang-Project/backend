package backend.spring.service;

import backend.spring.domain.Movie;
import backend.spring.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service  //빈으로 등록
public class MovieService {
    private final MovieRepository movieRepository;

    public void save() {
        Movie user = new Movie("영화이름", "이미지 위치");
        movieRepository.save(user);
    }
}
