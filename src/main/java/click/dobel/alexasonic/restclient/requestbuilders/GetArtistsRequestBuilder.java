package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.ArtistsID3;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public class GetArtistsRequestBuilder extends AbstractSubsonicRequestBuilder<GetArtistsRequestBuilder, ArtistsID3> {

    public GetArtistsRequestBuilder(final SubsonicCredentials configuration) {
        super(configuration, "getArtists");
    }

    public GetArtistsRequestBuilder withMusicFolderId(final int musicFolderId) {
        return with(PARAM_MUSIC_FOLDER_ID, musicFolderId);
    }

}
