package training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Callable;

/**
 * Created by Łukasz on 2015-06-17.
 */
@RestController
public class TestEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TestService testService;

    public TestEndpoint(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping("/test")
    @ResponseBody
    public Callable<String> test() {
        return () -> {
            Thread.sleep(1000);
            return "Hello";
        };
    }

    @RequestMapping("/test2")
    public DeferredResult<String> test2(@RequestParam String value) throws Exception {
        logger.info("Received request with value {}", value);

        return testService.serve(value);
    }
}
