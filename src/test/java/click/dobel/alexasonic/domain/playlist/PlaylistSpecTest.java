package click.dobel.alexasonic.domain.playlist;

import click.dobel.alexasonic.exception.AlexaSonicException;
import com.greghaskins.spectrum.Spectrum;
import org.junit.runner.RunWith;

import java.util.NoSuchElementException;

import static com.greghaskins.spectrum.Spectrum.describe;
import static com.greghaskins.spectrum.Spectrum.it;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(Spectrum.class)
public class PlaylistSpecTest {
    {

        final String EXISTING_1 = "one";
        final String EXISTING_2 = "two";
        final String NON_EXISTING_1 = "n_one";

        describe(Playlist.class.getSimpleName(), () -> {

            describe("#get", () -> {
                it("returns an existing item", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.get(EXISTING_1)).isEqualTo(EXISTING_1);
                });
                it("fails with a NoSuchElementException for a non-existing item", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(NoSuchElementException.class)
                        .isThrownBy(() -> playlist.get(NON_EXISTING_1));
                });
            });

            describe("#hasItem", () -> {
                it("returns true for an existing item", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.hasItem(EXISTING_1)).isTrue();
                });
                it("returns false for a non-existing item", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.hasItem(NON_EXISTING_1)).isFalse();
                });
            });

            describe("#nextOf", () -> {
                it("returns the next item when one exists", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.nextOf(EXISTING_1)).isEqualTo(EXISTING_2);
                });
                it("fails with a NoSuchElementException when the current item does not exist", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(NoSuchElementException.class)
                        .isThrownBy(() -> playlist.nextOf(NON_EXISTING_1));
                });
                it("fails with a AlexaSonicException when there is no next item", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(AlexaSonicException.class)
                        .isThrownBy(() -> playlist.nextOf(EXISTING_2))
                        .withMessage(Playlist.MESSAGEKEY_LAST_SONG);
                });
            });

            describe("#hasNext", () -> {
                it("returns true for an existing next item", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.hasNext(EXISTING_1)).isTrue();
                });
                it("returns false when there is no next item", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.hasNext(EXISTING_2)).isFalse();
                });
                it("fails with a NoSuchElementException when the current item does not exist", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(NoSuchElementException.class)
                        .isThrownBy(() -> playlist.hasNext(NON_EXISTING_1));
                });
            });

            describe("#previousOf", () -> {
                it("returns the previous item when one exists", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.previousOf(EXISTING_2)).isEqualTo(EXISTING_1);
                });
                it("fails with a NoSuchElementException when the current item does not exist", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(NoSuchElementException.class)
                        .isThrownBy(() -> playlist.previousOf(NON_EXISTING_1));
                });
                it("fails with a AlexaSonicException when there is no next item", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(AlexaSonicException.class)
                        .isThrownBy(() -> playlist.previousOf(EXISTING_1))
                        .withMessage(Playlist.MESSAGEKEY_FIRST_SONG);
                });
            });

            describe("#hasPrevious", () -> {
                it("returns true for an existing previous item", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.hasPrevious(EXISTING_2)).isTrue();
                });
                it("returns false when there is no previous item", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.hasPrevious(EXISTING_1)).isFalse();
                });
                it("fails with a NoSuchElementException when the current item does not exist", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(NoSuchElementException.class)
                        .isThrownBy(() -> playlist.hasPrevious(NON_EXISTING_1));
                });
            });

            describe("#first", () -> {
                it("returns the first item of a playlist", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.first()).isEqualTo(EXISTING_1);
                });
                it("fails with an AlexaSonicException when the playlist is empty.", () -> {
                    final Playlist playlist = new Playlist();
                    assertThatExceptionOfType(AlexaSonicException.class)
                        .isThrownBy(() -> playlist.first())
                        .withMessage(Playlist.MESSAGEKEY_EMPTY);
                });
            });

            describe("#clear", () -> {
                it("clears the playlist", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.first()).isEqualTo(EXISTING_1);
                    playlist.clear();
                    assertThatExceptionOfType(AlexaSonicException.class)
                        .isThrownBy(() -> playlist.first())
                        .withMessage(Playlist.MESSAGEKEY_EMPTY);
                });
            });

            describe("#add", () -> {
                it("adds an item to the playlist", () -> {
                    final Playlist playlist = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(playlist.hasItem(NON_EXISTING_1)).isFalse();
                    playlist.add(NON_EXISTING_1);
                    assertThat(playlist.hasItem(NON_EXISTING_1)).isTrue();
                });
            });
        });
    }
}
