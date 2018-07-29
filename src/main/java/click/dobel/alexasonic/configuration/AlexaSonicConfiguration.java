package click.dobel.alexasonic.configuration;

import com.amazon.ask.Skill;
import com.amazon.ask.attributes.persistence.PersistenceAdapter;
import com.amazon.ask.builder.CustomSkillBuilder;
import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.servlet.SkillServlet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(SubsonicCredentials.class)
public class AlexaSonicConfiguration {

    @Value("${application.id:''}")
    private String applicationId;

    @Autowired
    private final List<RequestHandler> requestHandlers = new ArrayList<>();

    @Autowired(required = false)
    private final List<ExceptionHandler> exceptionHandlers = new ArrayList<>();

    @Bean
    public ServletRegistrationBean<SkillServlet> registerServlet(
        final List<RequestHandler> requestHandlers,
        final List<ExceptionHandler> exceptionHandlers,
        final PersistenceAdapter persistenceAdapter
    ) {
        if (StringUtils.isBlank(applicationId)) {
            throw new IllegalArgumentException("Skill ID not set.");
        }

        final Skill skill = new CustomSkillBuilder()
            .withSkillId(applicationId)
            .addExceptionHandlers(exceptionHandlers)
            .addRequestHandlers(requestHandlers)
            .withPersistenceAdapter(persistenceAdapter)
            .build();

        final SkillServlet servlet = new SkillServlet(skill);

        return new ServletRegistrationBean<>(servlet, "/alexa");
    }
}
