package backend.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.entity.TodayWord;

public interface TodayWordRepository extends JpaRepository<TodayWord, Long> {
	Optional<TodayWord> findByEmotionAndStyleAndTone(String emotion, String style, String tone);
}
