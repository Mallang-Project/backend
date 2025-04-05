package backend.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.entity.Visitor;

import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    List<Visitor> findAll();
}
