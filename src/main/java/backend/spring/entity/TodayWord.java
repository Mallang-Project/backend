package backend.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(nullable = false)
	private String emotion;

	@Column(nullable = false)
	private String style;

	@Column(nullable = false)
	private String tone;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String word;

	public TodayWord(String emotion, String style, String tone, String word) {
		this.emotion = emotion;
		this.style = style;
		this.tone = tone;
		this.word = word;
	}
}
