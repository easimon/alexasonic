package click.dobel.alexasonic.i18n;

import com.greghaskins.spectrum.Spectrum;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static com.greghaskins.spectrum.Spectrum.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(Spectrum.class)
public class MessagesSpecTest {
    private static final Locale LOCALE = Locale.ENGLISH;

    private static final String FOUND_KEY = "foundkey";
    private static final String FOUND_MESSAGE = "foundmessage";

    private static final String DEFAULT_KEY = "defaultkey";
    private static final String DEFAULT_MESSAGE = "defaultmessage";

    private static final String NO_SUCH_KEY = "nosuchkey";

    {
        describe("Messages", () -> {
            final MessageSource messageSource = mock(MessageSource.class);

            describe("#Messages(messageSource)", () -> {
                it("throws a NullpointerException when initialized without messageSource", () -> {
                    assertThatNullPointerException().isThrownBy(() -> new Messages(null));
                });
            });

            beforeEach(() -> {
                reset(messageSource);
                when(messageSource.getMessage(any(String.class), any(Object[].class), any(Locale.class)))
                    .thenThrow(NoSuchMessageException.class);
                when(messageSource.getMessage(eq(FOUND_KEY), any(Object[].class), any(Locale.class)))
                    .thenReturn(FOUND_MESSAGE);
                when(messageSource.getMessage(eq(DEFAULT_KEY), any(Object[].class), any(Locale.class)))
                    .thenReturn(DEFAULT_MESSAGE);
            });

            describe("#getMessage(code, locale, args)", () -> {
                it("returns the message for the given key", () -> {
                    final Messages messages = new Messages(messageSource);
                    assertThat(messages.getMessage(FOUND_KEY, LOCALE)).isEqualTo(FOUND_MESSAGE);
                });
                it("throws a NoSuchMessageException for unknown keys", () -> {
                    final Messages messages = new Messages(messageSource);
                    assertThatExceptionOfType(NoSuchMessageException.class)
                        .isThrownBy(() -> {
                            messages.getMessage(NO_SUCH_KEY, LOCALE);
                        });
                });
            });
            describe("#getMessage(code, defaultCode, locale, args)", () -> {
                it("returns the message for the given key", () -> {
                    final Messages messages = new Messages(messageSource);
                    assertThat(messages.getMessage(FOUND_KEY, DEFAULT_KEY, LOCALE)).isEqualTo(FOUND_MESSAGE);
                });
                it("returns the default message for an unknown key", () -> {
                    final Messages messages = new Messages(messageSource);
                    assertThat(messages.getMessage(NO_SUCH_KEY, DEFAULT_KEY, LOCALE)).isEqualTo(DEFAULT_MESSAGE);
                });
                it("throws a NoSuchMessageException for unknown default keys", () -> {
                    final Messages messages = new Messages(messageSource);
                    assertThatExceptionOfType(NoSuchMessageException.class)
                        .isThrownBy(() -> {
                            messages.getMessage(NO_SUCH_KEY, NO_SUCH_KEY, LOCALE);
                        });
                });
            });

        });
    }
}
