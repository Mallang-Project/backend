package backend.spring.dto.object;

import backend.spring.entity.Movie;

public record TmdbData(
        String genre,

        String title,
        String summary,
        String year,
        String image,
        String score,

        String hour,  //movie detail 정보
        String director,
        String actor1,
        String actor2
) {
    /** 기존 데이터에 새 genre만 덮어쓴 복사본을 반환 */
    public TmdbData withGenre(String genre) {
        return new TmdbData(
                genre,
                title,
                summary,
                year,
                image,
                score,
                hour,
                director,
                actor1,
                actor2
        );
    }
}