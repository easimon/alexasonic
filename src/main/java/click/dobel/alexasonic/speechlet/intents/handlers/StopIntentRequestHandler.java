package click.dobel.alexasonic.speechlet.intents.handlers;

import org.springframework.stereotype.Component;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;

import click.dobel.alexasonic.speechlet.SpeechletResponseUtils;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@Component
public class StopIntentRequestHandler implements IntentRequestHandler {

    private static final String INTENTNAME = "AMAZON.StopIntent";

    @Override
    public String getIntentName() {
        return INTENTNAME;
    }

    @Override
    public SpeechletResponse onIntent(final RequestContext<IntentRequest> context) {
        return SpeechletResponseUtils.newStopDirective();
    }

}
