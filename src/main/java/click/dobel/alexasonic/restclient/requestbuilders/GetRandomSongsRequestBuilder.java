package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.Songs;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public class GetRandomSongsRequestBuilder extends AbstractSubsonicRequestBuilder<GetRandomSongsRequestBuilder, Songs> {

    public GetRandomSongsRequestBuilder(final SubsonicCredentials configuration) {
        super(configuration, "getRandomSongs");
    }

    /**
     * The maximum number of songs to return. Default 10, Max 500.
     * 
     * @param size
     *            number of songs to return.
     * @return this
     */
    public GetRandomSongsRequestBuilder withSize(final int size) {
        return with(PARAM_SIZE, size);
    }

    /**
     * Only returns songs belonging to this genre.
     * 
     * @param genre
     *            the genre.
     * @return this
     */
    public GetRandomSongsRequestBuilder withGenre(final String genre) {
        return with(PARAM_GENRE, genre);
    }

    /**
     * Only return songs published after or in this year.
     * 
     * @param year
     *            the year.
     * @return this
     */
    public GetRandomSongsRequestBuilder withFromYear(final int year) {
        return with(PARAM_FROM_YEAR, year);
    }

    /**
     * Only return songs published before or in this year.
     * 
     * @param year
     *            the year.
     * @return this
     */
    public GetRandomSongsRequestBuilder withToYear(final int year) {
        return with(PARAM_TO_YEAR, year);
    }

    /**
     * Only return songs in the music folder with the given ID. See
     * {@link GetMusicFoldersRequestBuilder}.
     * 
     * @param id
     *            the music folder id.
     * @return this.
     */
    public GetRandomSongsRequestBuilder withMusicFolderId(final int id) {
        return with(PARAM_MUSIC_FOLDER_ID, id);
    }

}
