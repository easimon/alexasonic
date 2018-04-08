package click.dobel.alexasonic.speechlet.intents.handlers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;

import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@Component
public class UnknownIntentRequestHandler implements IntentRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnknownIntentRequestHandler.class);

    private static final String MESSAGEKEY_UNKNOWN_INTENT = "UnknownIntent.NotUnderstood";

    public static final String INTENTNAME = "<<UNKNOWN>>";

    private final ErrorHandler errorHandler;

    @Autowired
    public UnknownIntentRequestHandler(final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public String getIntentName() {
        return INTENTNAME;
    }

    @Override
    public SpeechletResponse onIntent(final RequestContext<IntentRequest> context) {
        final String intentName = Optional //
                .ofNullable(context.getRequest()) //
                .map(request -> request.getIntent()) //
                .map(intent -> intent.getName()) //
                .orElse(null);

        LOGGER.error("Unknown Intent received: {}", intentName);
        return errorHandler.onError(context, MESSAGEKEY_UNKNOWN_INTENT, context.getLocale());
    }

}
