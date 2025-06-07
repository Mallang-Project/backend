package backend.spring.dto.object;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbDetailRaw(
        String title,
        @JsonProperty("overview") String summary,
        @JsonProperty("release_date") String releaseDate,
        @JsonProperty("poster_path") String posterPath,
        @JsonProperty("vote_average") double vote,
        Integer runtime,
        Credits credits) {

    /** Raw → 최종 TMDBResponseDto 로 변환 */
    public TmdbData toDto() {
        String image = (posterPath != null)
                ? "https://image.tmdb.org/t/p/w500" + posterPath
                : null;

        // 감독
        String director = credits.crew().stream()
                .filter(c -> "Director".equals(c.job()))
                .map(Crew::name)
                .findFirst()
                .orElse(null);

        // 배우 1, 2
        String actor1 = !credits.cast().isEmpty() ? credits.cast().get(0).name() : null;
        String actor2 = credits.cast().size() > 1 ? credits.cast().get(1).name() : null;

        return new TmdbData(
                null,
                title,
                summary,
                releaseDate,
                image,
                String.format("%.1f", vote),
                runtime != null ? runtime.toString() : null,
                director,
                actor1,
                actor2
        );
    }

    //하위 구조
    public record Credits(List<Crew> crew, List<Cast> cast) {}
    public record Crew(String job, String name) {}
    public record Cast(String name) {}
}
