package click.dobel.alexasonic.restclient;

import click.dobel.alexasonic.restclient.requestbuilders.RequestBuilders;
import click.dobel.alexasonic.restclient.responseconverters.ResponseConverters;
import click.dobel.alexasonic.test.AbstractAlexaSonicIntegrationTest;
import com.palantir.docker.compose.DockerComposeRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.subsonic.restapi.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class SubsonicRestClientIntegrationTest extends AbstractAlexaSonicIntegrationTest {

    @ClassRule
    public static DockerComposeRule docker = dockerClassRule();

    @Autowired
    private SubsonicRestClient restClient;

    @Test
    public void testPing() {
        final ResponseStatus response = restClient.execute(
            RequestBuilders.ping(),
            Response::getStatus,
            getTestCredentials()
        );

        assertThat(response).isEqualTo(ResponseStatus.OK);
    }

    @Test
    public void testGetArtists() {
        final ArtistsID3 artists = restClient.execute(
            RequestBuilders.getArtists(),
            ResponseConverters.ARTISTS,
            getTestCredentials()
        );

        assertThat(artists).isNotNull();
        assertThat(artists.getIndex()).hasSize(2);

        assertThat(artists.getIndex().get(0).getName()).isEqualTo("A");
        assertThat(artists.getIndex().get(0).getArtist()).hasSize(1);

        assertThat(artists.getIndex().get(1).getName()).isEqualTo("H");
        assertThat(artists.getIndex().get(1).getArtist()).hasSize(1);
    }

    @Test
    public void testGetArtistsFlat() {
        final List<ArtistID3> artists = restClient.executeAndFlatten(
            RequestBuilders.getArtists(),
            ResponseConverters.ARTISTS,
            getTestCredentials()
        );

        assertThat(artists).isNotNull();
        assertThat(artists).hasSize(2);
    }

    @Test
    public void testGetRandomSongs() {
        final Songs songs = restClient.execute(
            RequestBuilders.getRandomSongs().withSize(10),
            Response::getRandomSongs,
            getTestCredentials()
        );

        assertThat(songs).isNotNull();
        assertThat(songs.getSong()).hasSize(10);
    }

    /**
     * TODO: unclear on how to really test this.
     */
    @Test
    public void testStream() {
        final Songs songs = restClient.execute(
            RequestBuilders.getRandomSongs().withSize(10),
            Response::getRandomSongs,
            getTestCredentials()
        );
        final Child song = songs.getSong().get(0);
        final String songId = song.getId();

        System.out.println(RequestBuilders.stream().withId(songId).getUri(getTestCredentials()));
    }
}
