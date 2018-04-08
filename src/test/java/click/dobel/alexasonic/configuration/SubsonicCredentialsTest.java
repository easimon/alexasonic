package click.dobel.alexasonic.configuration;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import click.dobel.alexasonic.test.AlexaSonicSpringTest;

@RunWith(SpringRunner.class)
public class SubsonicCredentialsTest extends AlexaSonicSpringTest {

    @Autowired
    private SubsonicCredentials configuration;

    @Test
    public void testConfigurationValuesNotNull() {
        assertThat(configuration.getUsername()).isNotNull();
        assertThat(configuration.getPassword()).isNotNull();
        assertThat(configuration.getUrl()).isNotNull();
    }

}
