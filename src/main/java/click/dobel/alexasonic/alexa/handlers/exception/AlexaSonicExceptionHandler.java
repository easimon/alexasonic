package click.dobel.alexasonic.alexa.handlers.exception;

import click.dobel.alexasonic.exception.AlexaSonicException;
import click.dobel.alexasonic.i18n.Messages;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import com.amazon.ask.dispatcher.exception.ExceptionHandler;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class AlexaSonicExceptionHandler implements ExceptionHandler {

    private static final String MESSAGEKEY_ERROR_GENERIC = "Error.Generic";

    private final Messages messages;

    @Autowired
    public AlexaSonicExceptionHandler(final Messages messages) {
        this.messages = messages;
    }

    @Override
    public boolean canHandle(final HandlerInput input, final Throwable throwable) {
        return throwable instanceof AlexaSonicException;
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final Throwable throwable) {

        final AlexaSonicException exception = (AlexaSonicException) throwable;
        final Locale locale = SpeechletRequestUtil.getLocale(input);
        final String errorMessage = messages.getMessage(
            exception.getErrorMessageKey(),
            MESSAGEKEY_ERROR_GENERIC,
            locale,
            exception.getErrorMessageArgs()
        );

        return input.getResponseBuilder()
            .withSpeech(errorMessage)
            .build();
    }
}
