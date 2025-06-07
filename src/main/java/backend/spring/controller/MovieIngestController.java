package backend.spring.controller;

import backend.spring.dto.object.MovieData;
import backend.spring.dto.object.TmdbData;
import backend.spring.dto.request.IngestRequestDto;
import backend.spring.dto.response.IngestResponseDto;
import backend.spring.service.MovieIngestService;
import backend.spring.service.client.OpenAiClient;
import backend.spring.service.client.TmdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/ingest")
@RequiredArgsConstructor
public class MovieIngestController {

    private final MovieIngestService ingest;
    private final TmdbClient tmdbClient;
    private final OpenAiClient openAiClient;

    @PostMapping("/{genre}")
    public Mono<IngestResponseDto> trigger(
            @PathVariable String genre,
            @RequestBody IngestRequestDto request) {
        return ingest.ingestGenre(genre, request.genreId(), request.page());
    }

    @GetMapping("/test/{movieId}")
    public ResponseEntity< Mono<TmdbData> > testFetchMovieDetail(@PathVariable String movieId){
        Mono<TmdbData> response = tmdbClient.fetchMovieDetail(movieId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/{genreId}/{page}")
    public ResponseEntity< Mono<List<String>> > testFetchMovieID(
            @PathVariable String genreId,
            @PathVariable int page){
        Mono<List<String>> response = tmdbClient.fetchMovieIds(genreId, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/gpt")
    public ResponseEntity< Mono<MovieData> > testTranslateGpt(@RequestBody TmdbData request){
        Mono<MovieData> response = openAiClient.translateAndTag(request);
        return ResponseEntity.ok(response);
    }

}
