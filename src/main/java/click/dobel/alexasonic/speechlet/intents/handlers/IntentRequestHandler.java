package click.dobel.alexasonic.speechlet.intents.handlers;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;

import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

public interface IntentRequestHandler {

    String getIntentName();

    SpeechletResponse onIntent(final RequestContext<IntentRequest> context);

}
