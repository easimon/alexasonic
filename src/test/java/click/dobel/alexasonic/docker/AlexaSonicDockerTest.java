package click.dobel.alexasonic.docker;

import static org.assertj.core.api.Assertions.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.palantir.docker.compose.DockerComposeRule;

import click.dobel.alexasonic.test.AbstractAlexaSonicIntegrationTest;

public class AlexaSonicDockerTest extends AbstractAlexaSonicIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlexaSonicDockerTest.class);

    @ClassRule
    public static DockerComposeRule docker = dockerClassRule();

    @Test
    public void test() {
        LOGGER.info("=====================");
        LOGGER.info("Docker ports exposed:");
        docker.containers().container(CONTAINER_AIRSONIC).ports().stream().forEach(port -> {
            LOGGER.info(String.format("H: %s I: %s E: %s", port.getIp(), port.getInternalPort(), port.getExternalPort()));
        });
        LOGGER.info("=====================");
        assertThat(docker.containers().container(CONTAINER_AIRSONIC).ports().stream().count()).isGreaterThan(0l);
    }

}
