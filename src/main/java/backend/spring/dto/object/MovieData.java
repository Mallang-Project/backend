package backend.spring.dto.object;

import backend.spring.entity.Movie;

public record MovieData(
        String genre,

        String title,
        String summary,
        String year,
        String image,
        String score,

        String hour,  //movie detail 정보
        String director,
        String actor1,
        String actor2,

        String emotion, //gpt
        String style
) {
    public Movie toMovieEntity(){
        return Movie.of(
                genre,
                title,
                summary,
                year,
                image,
                score,
                hour,
                director,
                actor1,
                actor2,
                emotion,
                style
        );
    }
}
