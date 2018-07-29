package click.dobel.alexasonic.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@RunWith(SpringRunner.class)
@SuppressWarnings({"PMD.AbstractClassWithoutAbstractMethod", "PMD.AbstractClassWithoutAnyMethod"})
public abstract class AbstractAlexaSonicSpringTest {

}
