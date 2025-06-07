package backend.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import backend.spring.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query(value = """
		SELECT * FROM (
			SELECT *,
				(CASE WHEN emotion = :emotion THEN 2 ELSE 0 END) +
				(CASE WHEN style = :style THEN 2 ELSE 0 END) +
				(CASE WHEN genre = :genre THEN 1 ELSE 0 END)
			AS match_count
			FROM movies
			WHERE genre <> :hate
			AND (
				emotion = :emotion OR
				style = :style OR
				genre = :genre
			)
		) AS temp
		ORDER BY match_count DESC, RAND()
		LIMIT 3
		""", nativeQuery = true)
	List<Movie> findRecommendedMovies(
		@Param("emotion") String emotion,
		@Param("style") String style,
		@Param("genre") String genre,
		@Param("hate") String hate
	);
}
