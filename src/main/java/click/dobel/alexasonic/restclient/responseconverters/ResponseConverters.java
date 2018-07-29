package click.dobel.alexasonic.restclient.responseconverters;

import org.subsonic.restapi.*;

import java.util.List;
import java.util.stream.Collectors;

public final class ResponseConverters {

    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private static final SubsonicResponseConverter<ResponseStatus> PING = response -> response.getStatus();

    private static final SubsonicResponseConverter<License> LICENSE = response -> response.getLicense();

    private static final SubsonicResponseConverter<Indexes> INDEXES = response -> response.getIndexes();

    private static final SubsonicResponseConverter<List<MusicFolder>> MUSIC_FOLDERS = response -> response.getMusicFolders()
        .getMusicFolder();

    private static final FlatteningSubsonicResponseConverter<ArtistsID3, List<ArtistID3>> ARTISTS = of(
        Response::getArtists,
        artist -> artist
            .getIndex()
            .stream()
            .flatMap(index ->
                index
                    .getArtist()
                    .stream())
            .collect(Collectors.toList())
    );

    private static final FlatteningSubsonicResponseConverter<Songs, List<Child>> RANDOM_SONGS = of(
        Response::getRandomSongs,
        Songs::getSong
    );

    private ResponseConverters() {
    }

    @SuppressWarnings("PMD.ShortMethodName")
    private static <T, F> FlatteningSubsonicResponseConverter<T, F> of(final SubsonicResponseConverter<T> converter,
                                                                       final SubsonicResponseFlattener<T, F> flattener) {
        return FlatteningSubsonicResponseConverter.of(converter, flattener);
    }

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
