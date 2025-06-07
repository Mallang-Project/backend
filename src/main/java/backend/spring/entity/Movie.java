package backend.spring.entity;

import backend.spring.entity.type.Emotion;
import backend.spring.entity.type.Genre;
import backend.spring.entity.type.Style;
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
@Table(name = "movies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movie_id", nullable = false)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String hour;

	@Column(nullable = false)
	private String year;

	@Column(nullable = false)
	private String image;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String summary;

	@Column(nullable = false)
	private Double score;

	@Column(nullable = false)
	private String director;

	@Column(nullable = false)
	private String actor1;

	@Column(nullable = false)
	private String actor2;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Genre genre;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Emotion emotion;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Style style;

	public Movie(String title, String hour, String year, String image, String summary, Double score, String director, String actor1, String actor2, Genre genre, Emotion emotion, Style style) {
		this.title = title;
		this.hour = hour;
		this.year = year;
		this.image = image;
		this.summary = summary;
		this.score = score;
		this.director = director;
		this.actor1 = actor1;
		this.actor2 = actor2;
		this.genre = genre;
		this.emotion = emotion;
		this.style = style;
	}

	public static Movie of(String genre, String title, String summary, String year, String image, String str_score, String hour, String director, String actor1, String actor2, String emotion, String style){
		Double score = Double.parseDouble(str_score);
		return new Movie(
				title,
				hour,
				year,
				image,
				summary,
				score,
				director,
				actor1,
				actor2,
				Genre.from(genre),
				Emotion.from(emotion),
				Style.from(style)
		);
	}
}
