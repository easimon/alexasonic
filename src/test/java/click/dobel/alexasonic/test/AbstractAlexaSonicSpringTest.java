package click.dobel.alexasonic.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@SuppressWarnings({ "PMD.AbstractClassWithoutAbstractMethod", "PMD.AbstractClassWithoutAnyMethod" })
public abstract class AbstractAlexaSonicSpringTest {

}
