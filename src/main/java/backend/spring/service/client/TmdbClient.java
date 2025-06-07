package backend.spring.service.client;

import backend.spring.dto.object.IdListRaw;
import backend.spring.dto.object.TmdbDetailRaw;
import backend.spring.dto.object.TmdbData;
import backend.spring.exception.CustomException;
import backend.spring.exception.ResponseCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Component
public class TmdbClient {

    private final WebClient tmdbWebClient;
    public TmdbClient ( @Qualifier("tmdbWebClient") WebClient tmdbWebClient){
        this.tmdbWebClient = tmdbWebClient;
    }

    /* 1) discover → id 목록 */
    public Mono<List<String>> fetchMovieIds(String genreId, int page) {
        return tmdbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("language", "en-US")
                        .queryParam("with_genres", genreId)
                        .queryParam("include_adult", "false")
                        .queryParam("sort_by", "popularity.desc")
                        .queryParam("page", page)
                        .build())
                .exchangeToMono(this::handleIdsResponse)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)));
    }

    /* 2) detail + credits */
    public Mono<TmdbData> fetchMovieDetail(String movieId) {
        return tmdbWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .queryParam("language", "en-US")
                        .queryParam("append_to_response", "credits")
                        .build(movieId))
                .exchangeToMono(this::handleDetailResponse)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)));
    }

    /* ===== 공통 처리 ===== */

    private Mono<List<String>> handleIdsResponse(ClientResponse res) {
        if (res.statusCode().is2xxSuccessful()) {
            return res.bodyToMono(IdListRaw.class).map(IdListRaw::toIdList);
        }
        return res.createException()         // WebClientResponseException 으로 변환
                .flatMap(ex -> Mono.error(mapException(ex)));
    }

    private Mono<TmdbData> handleDetailResponse(ClientResponse res) {
        if (res.statusCode().is2xxSuccessful()) {
            return res.bodyToMono(TmdbDetailRaw.class)
                    .map(TmdbDetailRaw::toDto);
        }
        return res.createException()
                .flatMap(ex -> Mono.error(mapException(ex)));
    }

    /** ★ 핵심: HTTP → ResponseCode → CustomException */
    private RuntimeException mapException(WebClientResponseException ex) {
        return switch (ex.getStatusCode()) {
            case TOO_MANY_REQUESTS -> new CustomException(ResponseCode.TMDB_RATE_LIMIT);
            case NOT_FOUND         -> new CustomException(ResponseCode.TMDB_NOT_FOUND);
            case BAD_REQUEST       -> new CustomException(ResponseCode.INVALID_FORMAT);
            default                -> new CustomException(ResponseCode.TMDB_API_ERROR);
        };
    }
}
