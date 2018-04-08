package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.Indexes;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public class GetIndexesRequestBuilder extends AbstractSubsonicRequestBuilder<GetIndexesRequestBuilder, Indexes> {

    public GetIndexesRequestBuilder(final SubsonicCredentials configuration) {
        super(configuration, "getIndexes");
    }

    public GetIndexesRequestBuilder withMusicFolderId(final int musicFolderId) {
        return with(PARAM_MUSIC_FOLDER_ID, musicFolderId);
    }

    public GetIndexesRequestBuilder ifModifiedSince(final long epochMillis) {
        return with(PARAM_IF_MODIFIED_SINCE, epochMillis);
    }

}
