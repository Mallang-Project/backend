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

	@Column(nullable = false)
	private String genre;

	@Column(nullable = false)
	private String origin;

	@Column(nullable = false)
	private String emotion;

	@Column(nullable = false)
	private String style;
}
