package click.dobel.alexasonic.restclient.responseconverters;

import org.subsonic.restapi.ArtistID3;
import org.subsonic.restapi.ArtistsID3;
import org.subsonic.restapi.Child;
import org.subsonic.restapi.MusicFolder;
import org.subsonic.restapi.Response;
import org.subsonic.restapi.ResponseStatus;
import org.subsonic.restapi.Songs;

import java.util.List;
import java.util.stream.Collectors;

public final class ResponseConverters {

  public static final SubsonicResponseConverter<ResponseStatus> PING = Response::getStatus;

  public static final SubsonicResponseConverter<List<MusicFolder>> MUSIC_FOLDERS = response -> response
    .getMusicFolders()
    .getMusicFolder();

  public static final FlatteningSubsonicResponseConverter<ArtistsID3, List<ArtistID3>> ARTISTS = of(
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

  public static final FlatteningSubsonicResponseConverter<Songs, List<Child>> RANDOM_SONGS = of(
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
}
