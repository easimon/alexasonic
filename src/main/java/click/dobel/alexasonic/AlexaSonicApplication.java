package click.dobel.alexasonic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlexaSonicApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlexaSonicApplication.class);


    public static void main(final String[] args) {
        SpringApplication.run(AlexaSonicApplication.class, args);
    }
}
