package click.dobel.alexasonic;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.amazon.speech.Sdk;
import com.amazon.speech.speechlet.servlet.SpeechletServlet;

import click.dobel.alexasonic.speechlet.AlexaSonicSpeechlet;

@SpringBootApplication
public class AlexaSonicApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlexaSonicApplication.class);

    @Value("${application.ids:''}")
    private String applicationIds;

    private void registerApplicationIds() {
        if (StringUtils.isBlank(applicationIds)) {
            LOGGER.error("No application IDs set, I will not answer to Alexa Service.");
            return;
        }
        LOGGER.info("Setting Application Ids: {}", applicationIds);
        System.getProperties().put(Sdk.SUPPORTED_APPLICATION_IDS_SYSTEM_PROPERTY, applicationIds);
    }

    @Bean
    public ServletRegistrationBean<SpeechletServlet> registerServlet(final AlexaSonicSpeechlet alexaSonicSpeechlet) {
        registerApplicationIds();
        final SpeechletServlet speechletServlet = new SpeechletServlet();
        speechletServlet.setSpeechlet(alexaSonicSpeechlet);

        return new ServletRegistrationBean<>(speechletServlet, "/alexa");
    }

    public static void main(final String[] args) {
        SpringApplication.run(AlexaSonicApplication.class, args);
    }
}
