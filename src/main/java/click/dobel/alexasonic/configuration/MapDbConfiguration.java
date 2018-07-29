package click.dobel.alexasonic.configuration;

import click.dobel.alexasonic.alexa.MapDbPersistenceAdapter;
import click.dobel.alexasonic.mapdb.DbFactories;
import click.dobel.alexasonic.mapdb.DbFactory;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapDbConfiguration {

    @Value("${application.db.path}")
    private String dbFilesLocationString;

    @Bean
    public DbFactory dbFactory() {
        return DbFactories.file(dbFilesLocationString);
    }

    @Bean
    public MapDbPersistenceAdapter mapDbPersistenceAdapter() {
        return new MapDbPersistenceAdapter(
            dbFactory(),
            "sessions",
            SpeechletRequestUtil::getDeviceId);
    }
}
