package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.ArtistsID3;

public class GetArtistsRequestBuilder extends AbstractSubsonicRequestBuilder<GetArtistsRequestBuilder, ArtistsID3> {

  GetArtistsRequestBuilder() {
    super("getArtists");
  }

  public GetArtistsRequestBuilder withMusicFolderId(final int musicFolderId) {
    return with(RequestParameters.PARAM_MUSIC_FOLDER_ID, musicFolderId);
  }
}
