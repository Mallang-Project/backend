package backend.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.spring.entity.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

	Page<Visitor> findAllByReviewIsNotNull(Pageable pageable);

	@Query("SELECT COALESCE(AVG(v.score), 0) FROM Visitor v WHERE v.review IS NOT NULL")
	double getAverageScore();
}
