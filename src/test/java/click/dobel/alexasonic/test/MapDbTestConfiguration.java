package click.dobel.alexasonic.test;

import click.dobel.alexasonic.mapdb.DbFactories;
import click.dobel.alexasonic.mapdb.DbFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class MapDbTestConfiguration {

  @Bean
  @Primary
  public DbFactory inMemoryDbFactory() {
    return DbFactories.mem();
  }
}
