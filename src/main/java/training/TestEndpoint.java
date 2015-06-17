package training;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * Created by Łukasz on 2015-06-17.
 */
@Controller
public class TestEndpoint {

	@RequestMapping("/test")
	@ResponseBody
	public Callable<String> test() {
		return () -> {
			Thread.sleep(1000);
			return "Hello";
		};
	}

	@RequestMapping("/test2")
	@ResponseBody
	public DeferredResult<String> test2() throws Exception {
		DeferredResult<String> result = new DeferredResult<>();
		((Callable) () -> {
			Thread.sleep(1000);
			result.setResult("Hello");
			return null;
		}).call();

		return result;
	}
}
