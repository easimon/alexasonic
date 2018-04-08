package click.dobel.alexasonic.i18n;

import static com.greghaskins.spectrum.Spectrum.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Locale;

import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import com.greghaskins.spectrum.Spectrum;

@RunWith(Spectrum.class)
public class MessagesSpecTest {
    private static final Locale LOCALE = Locale.ENGLISH;

    private static final String FOUNDKEY = "foundkey";
    private static final String FOUNDMESSAGE = "foundmessage";

    private static final String DEFAULTKEY = "defaultkey";
    private static final String DEFAULTMESSAGE = "defaultmessage";

    private static final String NOSUCHKEY = "nosuchkey";
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
                when(messageSource.getMessage(any(String.class), any(Object[].class), any(Locale.class))) //
                        .thenThrow(NoSuchMessageException.class);
                when(messageSource.getMessage(eq(FOUNDKEY), any(Object[].class), any(Locale.class))) //
                        .thenReturn(FOUNDMESSAGE);
                when(messageSource.getMessage(eq(DEFAULTKEY), any(Object[].class), any(Locale.class))) //
                        .thenReturn(DEFAULTMESSAGE);
            });

            describe("#getMessage(code, locale, args)", () -> {
                it("returns the message for the given key", () -> {
                    final Messages messages = new Messages(messageSource);
                    assertThat(messages.getMessage(FOUNDKEY, LOCALE)).isEqualTo(FOUNDMESSAGE);
                });
                it("throws a NoSuchMessageException for unknown keys", () -> {
                    final Messages messages = new Messages(messageSource);
                    assertThatExceptionOfType(NoSuchMessageException.class) //
                            .isThrownBy(() -> {
                                messages.getMessage(NOSUCHKEY, LOCALE);
                            });
                });
            });
            describe("#getMessage(code, defaultCode, locale, args)", () -> {
                it("returns the message for the given key", () -> {
                    final Messages messages = new Messages(messageSource);
                    assertThat(messages.getMessage(FOUNDKEY, DEFAULTKEY, LOCALE)).isEqualTo(FOUNDMESSAGE);
                });
                it("returns the default message for an unknown key", () -> {
                    final Messages messages = new Messages(messageSource);
                    assertThat(messages.getMessage(NOSUCHKEY, DEFAULTKEY, LOCALE)).isEqualTo(DEFAULTMESSAGE);
                });
                it("throws a NoSuchMessageException for unknown default keys", () -> {
                    final Messages messages = new Messages(messageSource);
                    assertThatExceptionOfType(NoSuchMessageException.class) //
                            .isThrownBy(() -> {
                                messages.getMessage(NOSUCHKEY, NOSUCHKEY, LOCALE);
                            });
                });
            });

        });
    }
}
