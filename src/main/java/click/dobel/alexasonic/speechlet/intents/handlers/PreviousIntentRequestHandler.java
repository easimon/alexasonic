package click.dobel.alexasonic.speechlet.intents.handlers;

import org.springframework.stereotype.Component;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.interfaces.audioplayer.PlayBehavior;

import click.dobel.alexasonic.speechlet.SpeechletRequestUtils;
import click.dobel.alexasonic.speechlet.SpeechletResponseUtils;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@Component
public class PreviousIntentRequestHandler implements IntentRequestHandler {

    private static final String INTENTNAME = "AMAZON.PreviousIntent";

    @Override
    public String getIntentName() {
        return INTENTNAME;
    }

    @Override
    public SpeechletResponse onIntent(final RequestContext<IntentRequest> context) {
        final String currentToken = SpeechletRequestUtils.getAudioPlayerToken(context.getRequestEnvelope());
        final String previousUrl = context.getDeviceSession().getPlaylist().previousOf(currentToken);
        return SpeechletResponseUtils.newPlayDirective(previousUrl, PlayBehavior.REPLACE_ALL);
    }

}
