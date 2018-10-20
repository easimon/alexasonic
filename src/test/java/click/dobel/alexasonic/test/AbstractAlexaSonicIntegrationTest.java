package click.dobel.alexasonic.test;

import click.dobel.alexasonic.configuration.SubsonicCredentials;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.joda.time.Duration;
import org.junit.ClassRule;

import java.lang.reflect.Field;

public abstract class AbstractAlexaSonicIntegrationTest extends AbstractAlexaSonicSpringTest {

    protected static final String CONTAINER_AIRSONIC = "airsonic";
    private static final int CONTAINER_AIRSONIC_PORT = 4040;

    private static final String TEST_USERNAME = "admin";
    private static final String TEST_PASSWORD = "admin";
    private static final String TEST_URL_FORMAT = "http://$HOST:$EXTERNAL_PORT/";

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
                    (port) -> port.inFormat(TEST_URL_FORMAT)
                )
            )
            .saveLogsTo(LOGFILE_PATH)
            .build();
    }

    private DockerComposeRule dockerComposeRuleFromImplementingTestClass;

    private static final String EXCEPTION_MESSAGE = "Could not find a public static @ClassRule of type "
        + DockerComposeRule.class.getSimpleName();

    private synchronized DockerComposeRule getDockerComposeRuleFromImplementingTestClass() {
        if (this.dockerComposeRuleFromImplementingTestClass != null) {
            return this.dockerComposeRuleFromImplementingTestClass;
        }
        try {
            for (final Field field : this.getClass().getFields()) {
                if (field.isAnnotationPresent(ClassRule.class)) {
                    final Object classRule = field.get(null);
                    if (classRule instanceof DockerComposeRule) {
                        this.dockerComposeRuleFromImplementingTestClass = (DockerComposeRule) classRule;
                        return this.dockerComposeRuleFromImplementingTestClass;
                    }
                }
            }
            throw new IllegalStateException(EXCEPTION_MESSAGE);
        } catch (final RuntimeException | IllegalAccessException e) {
            throw new UnsupportedOperationException(EXCEPTION_MESSAGE, e);
        }
    }

    protected SubsonicCredentials getTestCredentials() {
        final String airsonicUrl = getDockerComposeRuleFromImplementingTestClass()
            .containers().container(CONTAINER_AIRSONIC).port(CONTAINER_AIRSONIC_PORT)
            .inFormat(TEST_URL_FORMAT);

        return new SubsonicCredentials(airsonicUrl, TEST_USERNAME, TEST_PASSWORD);
    }
}
