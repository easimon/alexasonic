package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.ArtistWithAlbumsID3;

public class GetArtistRequestBuilder extends AbstractSubsonicRequestBuilder<GetArtistRequestBuilder, ArtistWithAlbumsID3> {

    GetArtistRequestBuilder() {
        super("getArtist");
    }

    public GetArtistRequestBuilder withMusicFolderId(final int artistId) {
        return with(RequestParameters.PARAM_ID, artistId);
    }
}
