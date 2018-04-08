package click.dobel.alexasonic;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class AlexaSonicApplicationTest extends click.dobel.alexasonic.test.AlexaSonicSpringTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testApplicationContextBuilds() {
        assertThat(applicationContext).isNotNull();
    }

}
