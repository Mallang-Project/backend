package backend.spring.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "visitor")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Visitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "visitor_id", nullable = false)
	private Long id;

	@Column(nullable = false)
	private String nickname;

	private Integer score;

	@Column(length = 200)
	private String review;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
}
