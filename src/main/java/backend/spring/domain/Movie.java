package backend.spring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "movie")
@Getter //게터로 getId, getTitle, getContent 사용 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED)//producted타입으로 기본생성자
public class Movie {

    @Id //기본키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 방식 사용
    @Column(name = "id", nullable = false)
    private Long id; //게시물 일련번호

    @Column(name = "name", nullable = false)
    private String name; //게시물 제목

    @Column(name = "image_path", nullable = false)
    private String image_path; //게시물 내용

    @Builder
    public Movie(String name, String image_path) {  //빌더 패턴으로 객체 생성
        this.name = name;
        this.image_path = image_path;
    }

}
