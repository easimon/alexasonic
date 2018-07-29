package click.dobel.alexasonic.test;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.joda.time.Duration;

public abstract class AbstractAlexaSonicIntegrationTest extends AbstractAlexaSonicSpringTest {

    protected static final String CONTAINER_AIRSONIC = "airsonic";
    protected static final int CONTAINER_AIRSONIC_PORT = 4040;

    private static final String LOGFILE_PATH = "/tmp/dockerTestLogs";

    public static DockerComposeRule dockerClassRule() {
        return DockerComposeRule.builder()
            .file("src/test/docker/docker-compose.yml")
            .waitingForService(
                CONTAINER_AIRSONIC,
                HealthChecks.toHaveAllPortsOpen(),
                Duration.standardMinutes(5)
            )
            .waitingForService(
                CONTAINER_AIRSONIC,
                HealthChecks.toRespondOverHttp(
                    CONTAINER_AIRSONIC_PORT,
                    (port) -> port.inFormat("http://$HOST:$EXTERNAL_PORT/")
                )
            )
            .saveLogsTo(LOGFILE_PATH)
            .build();
    }

}
