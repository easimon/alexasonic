package click.dobel.alexasonic.test;

import org.joda.time.Duration;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;

public abstract class AbstractAlexaSonicIntegrationTest extends AbstractAlexaSonicSpringTest {

    public static final String CONTAINER_AIRSONIC = "airsonic";
    public static final int CONTAINER_AIRSONIC_PORT = 4040;

    public static final String LOGFILE_PATH = "/tmp/dockerTestLogs";

    public static DockerComposeRule dockerClassRule() {
        return DockerComposeRule.builder() //
                .file("src/test/docker/docker-compose.yml") //
                .waitingForService( //
                        CONTAINER_AIRSONIC, //
                        HealthChecks.toHaveAllPortsOpen(), //
                        Duration.standardMinutes(5) //
                ) //
                .waitingForService( //
                        CONTAINER_AIRSONIC, //
                        HealthChecks.toRespondOverHttp( //
                                CONTAINER_AIRSONIC_PORT, //
                                (port) -> port.inFormat("http://$HOST:$EXTERNAL_PORT/") //
                        ) //
                ) //
                .saveLogsTo(LOGFILE_PATH) //
                .build();
    }

}
