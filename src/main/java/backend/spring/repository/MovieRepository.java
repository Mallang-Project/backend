package backend.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	List<Movie> findAllByGenreNot(String genre);
}
