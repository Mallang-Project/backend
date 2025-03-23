package backend.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(nullable = false)
	private String emotion;

	@Column(nullable = false)
	private String style;

	@Column(nullable = false)
	private String genre;

	@Column(nullable = false)
	private String origin;

	@Column(nullable = false)
	private String hate;

}
