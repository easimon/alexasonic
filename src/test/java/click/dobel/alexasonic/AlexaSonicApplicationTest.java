package click.dobel.alexasonic;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class AlexaSonicApplicationTest extends click.dobel.alexasonic.test.AbstractAlexaSonicSpringTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testApplicationContextBuilds() {
        assertThat(context).isNotNull();
    }

}
