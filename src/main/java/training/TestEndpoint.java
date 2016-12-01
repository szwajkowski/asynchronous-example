package training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Callable;

@RestController
public class TestEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TestService testService;

    public TestEndpoint(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    @ResponseBody
    public Callable<String> test() {
        return () -> {
            Thread.sleep(1000);
            return "Hello";
        };
    }

    @GetMapping("/test2")
    public DeferredResult<String> test2(@RequestParam String value) {
        logger.info("Received request with value {}", value);

        return testService.serve(value);
    }

    @GetMapping("/test3")
    public DeferredResult<String> test3(@RequestParam String value) {
        return testService.reactorServe(value);
    }
    @GetMapping("/test4")
    public DeferredResult<String> test4(@RequestParam String value) {
        DeferredResult<String> result = new DeferredResult<>();
        testService.reactorServe2(value)
            .subscribe(result::setResult);
        return result;
    }
}
