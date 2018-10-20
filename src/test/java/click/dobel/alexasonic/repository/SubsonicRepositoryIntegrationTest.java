package click.dobel.alexasonic.repository;

import click.dobel.alexasonic.test.AbstractAlexaSonicIntegrationTest;
import com.palantir.docker.compose.DockerComposeRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.subsonic.restapi.ArtistID3;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SubsonicRepositoryIntegrationTest extends AbstractAlexaSonicIntegrationTest {

    @ClassRule
    public static DockerComposeRule docker = dockerClassRule();

    @Autowired
    private SubsonicRepository repository;

    @Test
    public void getAllArtists() {
        final List<ArtistID3> allArtists = repository.getAllArtists(getTestCredentials());
        assertThat(allArtists)
            .hasSize(2);
        assertThat(allArtists.stream().map(ArtistID3::getName).collect(Collectors.toList()))
            .containsExactlyInAnyOrder("Artist Number 5", "Horst Honk");
    }
}
