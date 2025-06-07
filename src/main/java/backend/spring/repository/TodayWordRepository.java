package backend.spring.repository;

import java.util.Optional;

import backend.spring.entity.type.Emotion;
import backend.spring.entity.type.Style;
import backend.spring.entity.type.Tone;
import org.springframework.data.jpa.repository.JpaRepository;

import backend.spring.entity.TodayWord;

public interface TodayWordRepository extends JpaRepository<TodayWord, Long> {
	Optional<TodayWord> findByEmotionAndStyleAndTone(Emotion emotion, Style style, Tone tone);

	default Optional<TodayWord> findByKeys(String emotionKey, String styleKey, String toneKey) {
		return findByEmotionAndStyleAndTone(
				Emotion.from(emotionKey),
				Style.from(styleKey),
				Tone.from(toneKey)
		);
	}
}
