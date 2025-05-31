package backend.spring.entity;

import backend.spring.entity.type.Emotion;
import backend.spring.entity.type.Style;
import backend.spring.entity.type.Tone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "today_word")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodayWord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "word_id", nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Emotion emotion;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Style style;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Tone tone;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String word;

	public TodayWord(Emotion emotion, Style style, Tone tone, String word) {
		this.emotion = emotion;
		this.style = style;
		this.tone = tone;
		this.word = word;
	}
	
	public static TodayWord of(String emotion, String style, String tone, String word) {
		return new TodayWord(
			Emotion.from(emotion),
			Style.from(style),
			Tone.from(tone),
			word
		);
	}
}
