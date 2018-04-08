package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.Songs;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public class GetSimilarSongsRequestBuilder
        extends AbstractSubsonicRequestBuilder<GetSimilarSongsRequestBuilder, Songs> {

    public GetSimilarSongsRequestBuilder(final SubsonicCredentials configuration) {
        super(configuration, "getSimilarSongs");
    }

    protected GetSimilarSongsRequestBuilder(final SubsonicCredentials configuration, final String restResourceName) {
        super(configuration, restResourceName);
    }

    public GetSimilarSongsRequestBuilder withArtistId(final int artistId) {
        return with(PARAM_ID, artistId);
    }

    public GetSimilarSongsRequestBuilder withCount(final int count) {
        return with(PARAM_COUNT, count);
    }

}
