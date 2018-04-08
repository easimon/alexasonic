package click.dobel.alexasonic.restclient.requestbuilders;

import java.util.List;

import org.subsonic.restapi.MusicFolder;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public class GetMusicFoldersRequestBuilder
        extends AbstractSubsonicRequestBuilder<GetMusicFoldersRequestBuilder, List<MusicFolder>> {

    public GetMusicFoldersRequestBuilder(final SubsonicCredentials configuration) {
        super(configuration, "getMusicFolders");
    }

}
