package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.MusicFolder;

import java.util.List;

public class GetMusicFoldersRequestBuilder
    extends AbstractSubsonicRequestBuilder<GetMusicFoldersRequestBuilder, List<MusicFolder>> {

    GetMusicFoldersRequestBuilder() {
        super("getMusicFolders");
    }
}
