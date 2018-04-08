package click.dobel.alexasonic.speechlet.intents;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;

import click.dobel.alexasonic.speechlet.intents.handlers.IntentRequestHandler;
import click.dobel.alexasonic.speechlet.intents.handlers.UnknownIntentRequestHandler;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@Component
public class IntentRequestRouter {

    private final Map<String, IntentRequestHandler> intentRequestHandlers;

    private final IntentRequestHandler unknownIntentRequestHandler;

    @Autowired
    public IntentRequestRouter(final Collection<IntentRequestHandler> intentRequestHandlers) {
        this.intentRequestHandlers = new HashMap<>();
        intentRequestHandlers.forEach(handler -> {
            if (this.intentRequestHandlers.containsKey(handler.getIntentName())) {
                throw new IllegalArgumentException( //
                        String.format("Duplicate IntentRequestHandler for IntentName %s.", //
                                handler.getIntentName()));
            }
            this.intentRequestHandlers.put(handler.getIntentName(), handler);
        });

        this.unknownIntentRequestHandler = Objects.requireNonNull( //
                this.intentRequestHandlers.get(UnknownIntentRequestHandler.INTENTNAME),
                "No UnknownIntentRequestHandler registered.");
    }

    public SpeechletResponse route(final RequestContext<IntentRequest> requestContext) {

        final IntentRequestHandler handler = Optional //
                .ofNullable(requestContext.getRequest()) //
                .map(request -> request.getIntent()) //
                .map(intent -> intent.getName()) //
                .map(intentRequestHandlers::get) //
                .orElse(unknownIntentRequestHandler);

        return handler.onIntent(requestContext);

    }

}
