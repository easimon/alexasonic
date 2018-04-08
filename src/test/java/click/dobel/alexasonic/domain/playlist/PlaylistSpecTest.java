package click.dobel.alexasonic.domain.playlist;

import static com.greghaskins.spectrum.Spectrum.*;
import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.runner.RunWith;

import com.greghaskins.spectrum.Spectrum;

import click.dobel.alexasonic.exception.AlexaSonicException;

@RunWith(Spectrum.class)
public class PlaylistSpecTest {
    {

        final String EXISTING_1 = "one";
        final String EXISTING_2 = "two";
        final String NONEXISTING_1 = "n_one";

        describe(Playlist.class.getSimpleName(), () -> {

            describe("#get", () -> {
                it("returns an existing item", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.get(EXISTING_1)).isEqualTo(EXISTING_1);
                });
                it("fails with a NoSuchElementException for a non-existing item", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(NoSuchElementException.class) //
                            .isThrownBy(() -> p.get(NONEXISTING_1));
                });
            });

            describe("#hasItem", () -> {
                it("returns true for an existing item", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.hasItem(EXISTING_1)).isTrue();
                });
                it("returns false for a non-existing item", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.hasItem(NONEXISTING_1)).isFalse();
                });
            });

            describe("#nextOf", () -> {
                it("returns the next item when one exists", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.nextOf(EXISTING_1)).isEqualTo(EXISTING_2);
                });
                it("fails with a NoSuchElementException when the current item does not exist", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(NoSuchElementException.class) //
                            .isThrownBy(() -> p.nextOf(NONEXISTING_1));
                });
                it("fails with a AlexaSonicException when there is no next item", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(AlexaSonicException.class) //
                            .isThrownBy(() -> p.nextOf(EXISTING_2)) //
                            .withMessage(Playlist.MESSAGEKEY_LAST_SONG);
                });
            });

            describe("#hasNext", () -> {
                it("returns true for an existing next item", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.hasNext(EXISTING_1)).isTrue();
                });
                it("returns false when there is no next item", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.hasNext(EXISTING_2)).isFalse();
                });
                it("fails with a NoSuchElementException when the current item does not exist", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(NoSuchElementException.class) //
                            .isThrownBy(() -> p.hasNext(NONEXISTING_1));
                });
            });

            describe("#previousOf", () -> {
                it("returns the previous item when one exists", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.previousOf(EXISTING_2)).isEqualTo(EXISTING_1);
                });
                it("fails with a NoSuchElementException when the current item does not exist", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(NoSuchElementException.class) //
                            .isThrownBy(() -> p.previousOf(NONEXISTING_1));
                });
                it("fails with a AlexaSonicException when there is no next item", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(AlexaSonicException.class) //
                            .isThrownBy(() -> p.previousOf(EXISTING_1)) //
                            .withMessage(Playlist.MESSAGEKEY_FIRST_SONG);
                });
            });

            describe("#hasPrevious", () -> {
                it("returns true for an existing previous item", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.hasPrevious(EXISTING_2)).isTrue();
                });
                it("returns false when there is no previous item", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.hasPrevious(EXISTING_1)).isFalse();
                });
                it("fails with a NoSuchElementException when the current item does not exist", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThatExceptionOfType(NoSuchElementException.class) //
                            .isThrownBy(() -> p.hasPrevious(NONEXISTING_1));
                });
            });

            describe("#first", () -> {
                it("returns the first item of a playlist", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.first()).isEqualTo(EXISTING_1);
                });
                it("fails with an AlexaSonicException when the playlist is empty.", () -> {
                    final Playlist p = new Playlist();
                    assertThatExceptionOfType(AlexaSonicException.class) //
                            .isThrownBy(() -> p.first()) //
                            .withMessage(Playlist.MESSAGEKEY_EMPTY);
                });
            });

            describe("#clear", () -> {
                it("clears the playlist", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.first()).isEqualTo(EXISTING_1);
                    p.clear();
                    assertThatExceptionOfType(AlexaSonicException.class) //
                            .isThrownBy(() -> p.first()) //
                            .withMessage(Playlist.MESSAGEKEY_EMPTY);
                });
            });

            describe("#add", () -> {
                it("adds an item to the playlist", () -> {
                    final Playlist p = new Playlist(EXISTING_1, EXISTING_2);
                    assertThat(p.hasItem(NONEXISTING_1)).isFalse();
                    p.add(NONEXISTING_1);
                    assertThat(p.hasItem(NONEXISTING_1)).isTrue();
                });
            });
        });
    }
}
