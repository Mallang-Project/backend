package backend.spring.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import backend.spring.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query(value = "SELECT * FROM (" +
		"SELECT *, " +
		"( (CASE WHEN emotion IN :tags THEN 1 ELSE 0 END) + " +
		"  (CASE WHEN style IN :tags THEN 1 ELSE 0 END) + " +
		"  (CASE WHEN genre IN :tags THEN 1 ELSE 0 END) + " +
		"  (CASE WHEN origin IN :tags THEN 1 ELSE 0 END) " +
		") AS match_count " +
		"FROM movies " +
		"WHERE genre <> :hate " +
		"AND (emotion IN :tags OR style IN :tags OR genre IN :tags OR origin IN :tags) " +
		") AS temp " +
		"ORDER BY match_count DESC, RAND() " +
		"LIMIT 3", nativeQuery = true)
	List<Movie> findRecommendedMovies(@Param("tags") Set<String> tags, @Param("hate") String hate);
}
