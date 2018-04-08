package click.dobel.alexasonic.speechlet.intents.handlers;

import static com.greghaskins.spectrum.dsl.specification.Specification.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Locale;

import org.junit.runner.RunWith;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.greghaskins.spectrum.Spectrum;

import click.dobel.alexasonic.exception.AlexaSonicException;
import click.dobel.alexasonic.i18n.Messages;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@RunWith(Spectrum.class)
public class ErrorHandlerSpecTest {

    private static final String TEST_ERROR_MESSAGE_ENGLISH = "TestMessage";
    private static final String TEST_ERROR_MESSAGE_GERMAN = "TestNachricht";
    private static final String TEST_MESSAGEKEY = "TestMessage.Key";

    {
        final Messages messages = mock(Messages.class);
        final RequestContext<?> requestContext = mock(RequestContext.class);

        describe("ErrorHandler", () -> {

            describe("#ErrorHandler(messages)", () -> {
                it("throws a NullpointerException when initialized without messages", () -> {
                    assertThatNullPointerException().isThrownBy(() -> new ErrorHandler(null));
                });
            });

            beforeEach(() -> {
                reset(messages, requestContext);
                when(messages.getMessage(eq(TEST_MESSAGEKEY), anyString(), eq(Locale.ENGLISH), any(Object[].class))) //
                        .thenReturn(TEST_ERROR_MESSAGE_ENGLISH);
                when(messages.getMessage(eq(TEST_MESSAGEKEY), anyString(), eq(Locale.GERMAN), any(Object[].class))) //
                        .thenReturn(TEST_ERROR_MESSAGE_GERMAN);
            });

            context("for the German locale", () -> {
                beforeEach(() -> {
                    when(requestContext.getLocale()) //
                            .thenReturn(Locale.GERMAN);
                });

                describe("#onError(context, messageKey)", () -> {
                    it("returns a German plaintext speech containing the error message identified by the message key",
                            () -> {
                                final ErrorHandler errorHandler = new ErrorHandler(messages);
                                final SpeechletResponse response = errorHandler.onError(requestContext,
                                        TEST_MESSAGEKEY);
                                assertThat(response.getOutputSpeech()).isInstanceOf(PlainTextOutputSpeech.class);
                                final PlainTextOutputSpeech plaintext = (PlainTextOutputSpeech) response
                                        .getOutputSpeech();
                                assertThat(plaintext.getText()).isEqualTo(TEST_ERROR_MESSAGE_GERMAN);
                            });
                });

                describe("#onError(context, exception)", () -> {
                    it("returns a German plaintext speech containing the error message identified by the exception message",
                            () -> {
                                final ErrorHandler errorHandler = new ErrorHandler(messages);
                                final SpeechletResponse response = errorHandler.onError(requestContext,
                                        new AlexaSonicException(TEST_MESSAGEKEY));
                                assertThat(response.getOutputSpeech()).isInstanceOf(PlainTextOutputSpeech.class);
                                final PlainTextOutputSpeech plaintext = (PlainTextOutputSpeech) response
                                        .getOutputSpeech();
                                assertThat(plaintext.getText()).isEqualTo(TEST_ERROR_MESSAGE_GERMAN);
                            });
                });
            });
            context("for the English locale", () -> {
                beforeEach(() -> {
                    when(requestContext.getLocale()) //
                            .thenReturn(Locale.ENGLISH);
                });

                describe("#onError(context, messageKey)", () -> {
                    it("returns an English plaintext speech containing the error message identified by the message key",
                            () -> {
                                final ErrorHandler errorHandler = new ErrorHandler(messages);
                                final SpeechletResponse response = errorHandler.onError(requestContext,
                                        TEST_MESSAGEKEY);
                                assertThat(response.getOutputSpeech()).isInstanceOf(PlainTextOutputSpeech.class);
                                final PlainTextOutputSpeech plaintext = (PlainTextOutputSpeech) response
                                        .getOutputSpeech();
                                assertThat(plaintext.getText()).isEqualTo(TEST_ERROR_MESSAGE_ENGLISH);
                            });
                });

                describe("#onError(context, exception)", () -> {
                    it("returns a English plaintext speech containing the error message identified by the exception message",
                            () -> {
                                final ErrorHandler errorHandler = new ErrorHandler(messages);
                                final SpeechletResponse response = errorHandler.onError(requestContext,
                                        new AlexaSonicException(TEST_MESSAGEKEY));
                                assertThat(response.getOutputSpeech()).isInstanceOf(PlainTextOutputSpeech.class);
                                final PlainTextOutputSpeech plaintext = (PlainTextOutputSpeech) response
                                        .getOutputSpeech();
                                assertThat(plaintext.getText()).isEqualTo(TEST_ERROR_MESSAGE_ENGLISH);
                            });
                });
            });
        });
    }
}
