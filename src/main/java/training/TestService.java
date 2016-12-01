package training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TestService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    DeferredResult<String> serve(String input) {
        DeferredResult<String> result = new DeferredResult<>();
        Observable.just("test")
                .subscribeOn(Schedulers.io())
                .map(t -> t + input)
                .delay(1, TimeUnit.SECONDS, Schedulers.io())
                .subscribe((result1) -> {
                    logger.info("returning result {}", result1);
                    result.setResult(result1);
                });

        return result;
    }

    DeferredResult<String> reactorServe(String value) {
        DeferredResult<String> result = new DeferredResult<>();

        Mono.fromCallable(testRepository::findAll)
                .subscribeOn(reactor.core.scheduler.Schedulers.elastic())
                .flatMap(Flux::fromIterable)
                .map(s -> s + value)
                .collectList()
                .map(list -> list.stream().collect(Collectors.joining(";")))
                .subscribe(result::setResult);
        return result;
    }

    Mono<String> reactorServe2(String value) {
        Flux<String> stringFlux = Flux.just(value)
                .flatMap(v -> testRepository.findAllReactive()
                        .map(s -> s + value));
        Mono<List<String>> inside = stringFlux
                .subscribeOn(reactor.core.scheduler.Schedulers.elastic())
                .map(s -> {
                    logger.info("inside");
                    return s + value;
                })
                .collectList();
        return inside
                .map(list -> list.stream().collect(Collectors.joining(";")));
    }
}
