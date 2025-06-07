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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "visitor_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitorTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tag_id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "visitor_id", nullable = false)
	private Visitor visitor;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Emotion emotion;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Style style;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Genre genre;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Genre hate;

	public VisitorTag(Visitor visitor, Emotion emotion, Style style, Genre genre, Genre hate) {
		this.visitor = visitor;
		this.emotion = emotion;
		this.style = style;
		this.genre = genre;
		this.hate = hate;
	}

	public static VisitorTag of(Visitor visitor, Emotion emotion, Style style, Genre genre, Genre hate) {
		VisitorTag tag = new VisitorTag();
		tag.visitor = visitor;
		tag.emotion = emotion;
		tag.style = style;
		tag.genre = genre;
		tag.hate = hate;
		return tag;
	}
}
