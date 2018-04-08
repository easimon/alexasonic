package click.dobel.alexasonic.restclient.responseconverters;

import java.util.List;
import java.util.stream.Collectors;

import org.subsonic.restapi.ArtistID3;
import org.subsonic.restapi.ArtistsID3;
import org.subsonic.restapi.Child;
import org.subsonic.restapi.Indexes;
import org.subsonic.restapi.License;
import org.subsonic.restapi.MusicFolder;
import org.subsonic.restapi.ResponseStatus;
import org.subsonic.restapi.Songs;

public final class ResponseConverters {

    private static <T, F> FlatteningSubsonicResponseConverter<T, F> of(final SubsonicResponseConverter<T> converter,
            final SubsonicResponseFlattener<T, F> flattener) {
        return FlatteningSubsonicResponseConverter.of(converter, flattener);
    }

    private static final SubsonicResponseConverter<ResponseStatus> PING = r -> r.getStatus();

    private static final SubsonicResponseConverter<License> LICENSE = r -> r.getLicense();

    private static final SubsonicResponseConverter<Indexes> INDEXES = r -> r.getIndexes();

    private static final SubsonicResponseConverter<List<MusicFolder>> MUSIC_FOLDERS = r -> r.getMusicFolders()
            .getMusicFolder();

    private static final FlatteningSubsonicResponseConverter<ArtistsID3, List<ArtistID3>> ARTISTS = of( //
            r -> r.getArtists(), //
            a -> a.getIndex().stream().flatMap(i -> i.getArtist().stream()).collect(Collectors.toList()) //
    );

    private static final FlatteningSubsonicResponseConverter<Songs, List<Child>> RANDOM_SONGS = of( //
            r -> r.getRandomSongs(), //
            r -> r.getSong() //
    );

    public static SubsonicResponseConverter<ResponseStatus> ping() {
        return PING;
    }

    public static SubsonicResponseConverter<License> getLicense() {
        return LICENSE;
    }

    public static SubsonicResponseConverter<Indexes> getIndexes() {
        return INDEXES;
    }

    public static SubsonicResponseConverter<List<MusicFolder>> getMusicFolders() {
        return MUSIC_FOLDERS;
    }

    public static FlatteningSubsonicResponseConverter<ArtistsID3, List<ArtistID3>> getArtists() {
        return ARTISTS;
    }

    public static FlatteningSubsonicResponseConverter<Songs, List<Child>> getRandomSongs() {
        return RANDOM_SONGS;
    }

}
