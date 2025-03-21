package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
