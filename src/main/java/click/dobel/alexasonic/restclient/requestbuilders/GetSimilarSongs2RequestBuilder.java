package click.dobel.alexasonic.restclient.requestbuilders;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public class GetSimilarSongs2RequestBuilder extends GetSimilarSongsRequestBuilder {

    public GetSimilarSongs2RequestBuilder(final SubsonicCredentials configuration) {
        super(configuration, "getSimilarSongs2");
    }

}
