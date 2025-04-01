package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.entity.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}
