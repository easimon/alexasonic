package click.dobel.alexasonic.util;

import com.greghaskins.spectrum.Spectrum;
import org.junit.runner.RunWith;

import static com.greghaskins.spectrum.Spectrum.*;
import static org.assertj.core.api.Assertions.*;


@RunWith(Spectrum.class)
public class StringsSpecTest {
    {
        describe(Strings.class.getSimpleName(), () -> {
            describe("#isNull", () -> {
                it("returns true for null Strings", () -> {
                    assertThat(Strings.isNull(null)).isTrue();
                });
                it("returns false for non-null Strings", () -> {
                    assertThat(Strings.isNull("")).isFalse();
                });
            });

            describe("#isEmpty", () -> {
                it("returns true for null Strings", () -> {
                    assertThat(Strings.isEmpty(null)).isTrue();
                });
                it("returns true for empty Strings", () -> {
                    assertThat(Strings.isEmpty("")).isTrue();
                });
                it("returns false for non-empty Strings", () -> {
                    assertThat(Strings.isEmpty("bla")).isFalse();
                });
            });

            describe("#left", () -> {
                it("returns null String when String is null.", () -> {
                    assertThat(Strings.left(null, 0)).isNull();
                });
                it("returns whole String when String is shorter then len.", () -> {
                    assertThat(Strings.left("123", 5)).isEqualTo("123");
                });
                it("returns leftmost part of String for longer Strings.", () -> {
                    assertThat(Strings.left("12345", 3)).isEqualTo("123");
                });
                it("returns empty String when len is 0.", () -> {
                    assertThat(Strings.left("123", 0)).isEqualTo("");
                });
                it("throws an exception for negative lengths", () -> {
                    assertThatIllegalArgumentException()
                        .isThrownBy(() -> Strings.left("123", -1))
                        .withMessage("Length must not be negative.");
                });
            });

            describe("#right", () -> {
                it("returns null String when String is null.", () -> {
                    assertThat(Strings.right(null, 0)).isNull();
                });
                it("returns whole String when String is shorter then len.", () -> {
                    assertThat(Strings.right("123", 5)).isEqualTo("123");
                });
                it("returns rightmost part of String for longer Strings.", () -> {
                    assertThat(Strings.right("12345", 3)).isEqualTo("345");
                });
                it("returns empty String when len is 0.", () -> {
                    assertThat(Strings.right("123", 0)).isEqualTo("");
                });
                it("throws an exception for negative lengths", () -> {
                    assertThatIllegalArgumentException()
                        .isThrownBy(() -> Strings.right("123", -1))
                        .withMessage("Length must not be negative.");
                });
            });
        });
    }
}