package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.entity.VisitorTag;

public interface VisitorTagRepository extends JpaRepository<VisitorTag, Long> {
}
