package click.dobel.alexasonic.docker;

import static org.assertj.core.api.Assertions.*;

import org.junit.ClassRule;
import org.junit.Test;

import com.palantir.docker.compose.DockerComposeRule;

import click.dobel.alexasonic.test.AlexaSonicIntegrationTest;

public class AlexaSonicDockerTest extends AlexaSonicIntegrationTest {

    @ClassRule
    public static DockerComposeRule docker = dockerClassRule();

    @Test
    public void test() {
        System.out.println("=====================");
        System.out.println("Docker ports exposed:");
        docker.containers().container(CONTAINER_AIRSONIC).ports().stream().forEach(p -> {
            System.out.println(String.format("H: %s I: %s E: %s", p.getIp(), p.getInternalPort(), p.getExternalPort()));
        });
        System.out.println("=====================");
        assertThat(docker.containers().container(CONTAINER_AIRSONIC).ports().stream().count()).isGreaterThan(0l);
    }

}
