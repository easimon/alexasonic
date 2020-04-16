package click.dobel.alexasonic;

import click.dobel.alexasonic.test.AbstractAlexaSonicSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AlexaSonicApplicationTest extends AbstractAlexaSonicSpringTest {

  @Autowired
  private ApplicationContext context;

  @Test
  public void applicationContextBuilds() {
    assertThat(context).isNotNull();
  }
}
