package training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("training")
public class AsynchronousExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsynchronousExampleApplication.class, args);
    }
}
