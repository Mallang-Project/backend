package backend.spring.service;

import backend.spring.dto.object.TmdbData;
import backend.spring.dto.response.IngestResponseDto;
import backend.spring.repository.MovieRepository;
import backend.spring.service.client.OpenAiClient;
import backend.spring.service.client.TmdbClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieIngestService {

    private final TmdbClient tmdb;
    private final OpenAiClient gpt;
    private final MovieRepository movieRepo;

    /** 장르·페이지 단위 전체 ingest */
    public Mono<IngestResponseDto> ingestGenre(String genre, String genreId, int page) {

        AtomicInteger ok = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();

        return tmdb.fetchMovieIds(genreId, page)        // id 목록 가져옴
                .flatMapMany(Flux::fromIterable)
                // id별 detail, gpt 호출 (10개 병렬)
                .parallel(10)
                .runOn(Schedulers.boundedElastic())
                .flatMap(id -> tmdb.fetchMovieDetail(id) //입력받은 장르를 새로 저장
                        .map(d -> d.withGenre(genre)))
                .flatMap(this::translateSave)
                .sequential()
                .doOnNext(v -> ok.incrementAndGet())
                .onErrorContinue((e, obj) -> {
                    fail.incrementAndGet();
                    log.error("❌ save 실패 id={} / 예외={}", obj, e.getMessage(), e);
                })
                .then(Mono.fromSupplier(() -> new IngestResponseDto(ok.get(), fail.get())));
    }

    /** detail → GPT → DB 저장 */
    private Mono<Void> translateSave(TmdbData detail) {
        return gpt.translateAndTag(detail)
                .flatMap(movieData ->
                        Mono.fromCallable(() -> movieRepo.save(movieData.toMovieEntity()))
                                .subscribeOn(Schedulers.boundedElastic())     // 블로킹 전용 스레드
                )
                .then();
    }
}