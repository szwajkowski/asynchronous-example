package training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

@Service
public class TestService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
}
