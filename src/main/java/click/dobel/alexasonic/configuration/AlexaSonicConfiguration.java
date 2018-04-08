package click.dobel.alexasonic.configuration;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@Configuration
@EnableConfigurationProperties(SubsonicCredentials.class)
public class AlexaSonicConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jaxbEnablingCustomizer() {
        return b -> b.modules(new JaxbAnnotationModule());
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(30);
        return messageSource;
    }

}
