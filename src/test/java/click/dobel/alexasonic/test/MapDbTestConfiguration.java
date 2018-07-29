package click.dobel.alexasonic.test;

import click.dobel.alexasonic.mapdb.DbFactories;
import click.dobel.alexasonic.mapdb.DbFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MapDbTestConfiguration {

    @Bean
    @Primary
    public DbFactory dbFactory() {
        return DbFactories.mem();
    }
}
