package click.dobel.alexasonic.configuration;

import click.dobel.alexasonic.test.AbstractAlexaSonicSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SubsonicCredentialsSpringTest extends AbstractAlexaSonicSpringTest {

  @Autowired
  private SubsonicCredentials configuration;

  @Test
  public void testConfigurationValuesNotNull() {
    assertThat(configuration.getUsername()).isNotNull();
    assertThat(configuration.getPassword()).isNotNull();
    assertThat(configuration.getUrl()).isNotNull();
  }
}
