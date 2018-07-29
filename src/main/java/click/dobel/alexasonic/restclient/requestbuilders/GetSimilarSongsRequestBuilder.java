package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.Songs;

public class GetSimilarSongsRequestBuilder extends AbstractSubsonicRequestBuilder<GetSimilarSongsRequestBuilder, Songs> {

    GetSimilarSongsRequestBuilder() {
        super("getSimilarSongs");
    }

    GetSimilarSongsRequestBuilder(final String restResourceName) {
        super(restResourceName);
    }

    public GetSimilarSongsRequestBuilder withArtistId(final int artistId) {
        return with(RequestParameters.PARAM_ID, artistId);
    }

    public GetSimilarSongsRequestBuilder withCount(final int count) {
        return with(RequestParameters.PARAM_COUNT, count);
    }

}
