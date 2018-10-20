package click.dobel.alexasonic.exception;

import com.greghaskins.spectrum.Spectrum;
import org.junit.runner.RunWith;

import static com.greghaskins.spectrum.Spectrum.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Spectrum.class)
public class AlexaSonicExceptionSpecTest {
    {

        describe("getErrorMessageKey()", () -> {
            final String expectedKey = "thekey";
            final AlexaSonicException exception = new AlexaSonicException(expectedKey, "one", 2, "three");

            it("returns the message key", () -> {
                assertThat(exception.getErrorMessageKey())
                    .isEqualTo(expectedKey);
            });
        });

        describe("getMessage()", () -> {
            final String expectedKey = "thekey";
            final AlexaSonicException exception = new AlexaSonicException(expectedKey, "one", 2, "three");

            it("returns the message key concatenated with the arguments", () -> {
                assertThat(exception.getMessage())
                    .isEqualTo(exception.getErrorMessageKey() + " [" + "one,2,three" + "]");
            });
        });

        describe("getErrorMessageArgs()", () -> {
            final Object[] expectedArgs = new Object[]{"one", "2", "three"};
            final AlexaSonicException exception = new AlexaSonicException("thekey", "one", "2", "three");

            it("returns the message arguments", () -> {
                assertThat(exception.getErrorMessageArgs())
                    .isInstanceOf(Object[].class)
                    .hasSize(3)
                    .hasOnlyElementsOfType(String.class)
                    .isEqualTo(expectedArgs);
            });
        });
    }
}