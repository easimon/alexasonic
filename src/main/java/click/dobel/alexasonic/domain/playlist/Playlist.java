package click.dobel.alexasonic.domain.playlist;

import click.dobel.alexasonic.exception.AlexaSonicException;
import com.google.common.annotations.VisibleForTesting;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("PMD.TooManyMethods")
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @VisibleForTesting
    static final String MESSAGE_PREFIX = "Playlist";
    @VisibleForTesting
    static final String MESSAGEKEY_EMPTY = MESSAGE_PREFIX + ".Empty";
    @VisibleForTesting
    static final String MESSAGEKEY_LAST_SONG = MESSAGE_PREFIX + ".LastSong";
    @VisibleForTesting
    static final String MESSAGEKEY_FIRST_SONG = MESSAGE_PREFIX + ".FirstSong";

    /*
     * TODO: a list is obviously the least efficient structure to retrieve current,
     * previous and next entry by a given token
     */
    private List<String> items;

    public Playlist() {
        this(new String[]{});
    }

    public Playlist(final String... items) {
        this.items = Collections.synchronizedList(new ArrayList<>(Arrays.asList(items)));
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(final List<String> items) {
        this.items = new ArrayList<>(items);
    }

    public void add(final String entry) {
        items.add(entry);
    }

    public String get(final String token) {
        return find(token)
            .orElseThrow(() -> new NoSuchElementException(String.format("No playlist entry with token %s found.", token)));
    }

    private Optional<String> find(final String token) {
        return items
            .stream()
            .filter(item -> token.equals(item))
            .findAny();
    }

    public boolean hasItem(final String token) {
        return find(token).isPresent();
    }

    @SuppressWarnings("PMD.NullAssignment")
    private Optional<String> findNextOf(final String token) {
        return Optional.of(get(token))
            .map(items::indexOf)
            .map(index -> index >= 0 ? index + 1 : null)
            .map(index -> index < items.size() ? items.get(index) : null);
    }

    public boolean hasNext(final String token) {
        return findNextOf(token).isPresent();
    }

    public String nextOf(final String token) {
        return findNextOf(token)
            .orElseThrow(() -> new AlexaSonicException(MESSAGEKEY_LAST_SONG));
    }

    @SuppressWarnings("PMD.NullAssignment")
    public Optional<String> findPreviousOf(final String token) {
        return Optional.of(get(token))
            .map(items::indexOf)
            .map(index -> index > 0 ? index - 1 : null)
            .map(items::get);
    }

    public boolean hasPrevious(final String token) {
        return findPreviousOf(token).isPresent();
    }

    public String previousOf(final String token) {
        return findPreviousOf(token)
            .orElseThrow(() -> new AlexaSonicException(MESSAGEKEY_FIRST_SONG));
    }

    public String first() {
        if (items.isEmpty()) {
            throw new AlexaSonicException(MESSAGEKEY_EMPTY);
        }
        return items.get(0);
    }

    public void clear() {
        items.clear();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Playlist)) {
            return false;
        }
        return Objects.equals(this.items, ((Playlist) other).items);
    }

    @Override
    public int hashCode() {
        return this.items.hashCode();
    }

}
