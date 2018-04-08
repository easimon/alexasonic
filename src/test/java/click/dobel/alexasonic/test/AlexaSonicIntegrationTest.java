package click.dobel.alexasonic.test;

import org.junit.ClassRule;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;

public abstract class AlexaSonicIntegrationTest extends AlexaSonicSpringTest {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder().file("src/test/docker/docker-compose.yml")
            .waitingForService("airsonic", HealthChecks.toHaveAllPortsOpen())
            .waitingForService("airsonic",
                    HealthChecks.toRespondOverHttp(4040, (port) -> port.inFormat("http://$HOST:$EXTERNAL_PORT")))
            .build();

}
