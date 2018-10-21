package click.dobel.alexasonic.repository;

import click.dobel.alexasonic.configuration.SubsonicCredentials;
import click.dobel.alexasonic.restclient.SubsonicRestClient;
import click.dobel.alexasonic.restclient.requestbuilders.RequestBuilders;
import click.dobel.alexasonic.restclient.responseconverters.ResponseConverters;
import org.springframework.stereotype.Repository;
import org.subsonic.restapi.ArtistID3;

import java.util.List;

@Repository
public class SubsonicRepository {
  private final SubsonicRestClient restClient;

  public SubsonicRepository(final SubsonicRestClient restClient) {
    this.restClient = restClient;
  }

  public List<ArtistID3> getAllArtists(final SubsonicCredentials subsonicCredentials) {
    return restClient.executeAndFlatten(
      RequestBuilders.getArtists(),
      ResponseConverters.ARTISTS,
      subsonicCredentials
    );
  }
}
