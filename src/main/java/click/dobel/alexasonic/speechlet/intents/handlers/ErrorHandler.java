package click.dobel.alexasonic.speechlet.intents.handlers;

import java.util.Locale;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.speech.speechlet.SpeechletRequest;
import com.amazon.speech.speechlet.SpeechletResponse;

import click.dobel.alexasonic.exception.AlexaSonicException;
import click.dobel.alexasonic.i18n.Messages;
import click.dobel.alexasonic.speechlet.SpeechletResponseUtils;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@Component
public class ErrorHandler {

    public static final String MESSAGEKEY_ERROR_GENERIC = "Error.Generic";

    private final Messages messages;

    @Autowired
    public ErrorHandler(final Messages messages) {
        Objects.requireNonNull(messages, "Messages may not be null");
        this.messages = messages;
    }

    public SpeechletResponse onError(final RequestContext<? extends SpeechletRequest> requestContext,
            final String messageKey, final Object... args) {
        final Locale locale = requestContext.getLocale();
        final String errorMessage = messages.getMessage(messageKey, MESSAGEKEY_ERROR_GENERIC, locale, args);
        return SpeechletResponseUtils.newPlaintextTellResponse(errorMessage);
    }

    public SpeechletResponse onError(final RequestContext<? extends SpeechletRequest> requestContext,
            final AlexaSonicException exception) {
        return onError(requestContext, exception.getErrorMessageKey(), exception.getErrorMessageArgs());
    }

}
