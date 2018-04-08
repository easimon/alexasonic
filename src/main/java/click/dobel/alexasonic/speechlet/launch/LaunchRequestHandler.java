package click.dobel.alexasonic.speechlet.launch;

import org.springframework.stereotype.Component;

import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SpeechletResponse;

import click.dobel.alexasonic.i18n.Messages;
import click.dobel.alexasonic.speechlet.SpeechletResponseUtils;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@Component
public class LaunchRequestHandler {

    private static String MESSAGEKEY_LAUNCH_GREETING = "LaunchRequest.Greeting";
    private static String MESSAGEKEY_LAUNCH_REPROMPT = "LaunchRequest.Reprompt";

    private final Messages messages;

    public LaunchRequestHandler(final Messages messages) {
        this.messages = messages;
    }

    public SpeechletResponse onLaunch(final RequestContext<LaunchRequest> requestContext) {

        final String message = messages.getMessage(MESSAGEKEY_LAUNCH_GREETING, requestContext.getLocale());
        final String reprompt = messages.getMessage(MESSAGEKEY_LAUNCH_REPROMPT, requestContext.getLocale());

        return SpeechletResponseUtils.newPlaintextAskResponse(message, reprompt);
    }

}
