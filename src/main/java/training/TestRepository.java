package training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;

@Component
public class TestRepository {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final List<String> list;

    public TestRepository() {
        this.list = Arrays.asList("first", "second", "third", "fourth");
    }

    public List<String> findAll() {
        logger.info("returning all values");
        return list;
    }

    public Flux<String> findAllReactive() {
        logger.info("returning all values reactive?");
        return Flux.fromIterable(list);
    }
}
