package click.dobel.alexasonic.alexa.handlers.lifecycle;

import click.dobel.alexasonic.alexa.handlers.AbstractDeviceSessionAwareRequestHandler;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.repository.DeviceSessionRepository;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.model.interfaces.playbackcontroller.PreviousCommandIssuedRequest;
import com.amazon.ask.request.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PreviousIntentRequestHandler extends AbstractDeviceSessionAwareRequestHandler {

    @Autowired
    public PreviousIntentRequestHandler(final DeviceSessionRepository deviceSessionRepository) {
        super(deviceSessionRepository);
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(
            Predicates.requestType(PreviousCommandIssuedRequest.class)
                .or(Predicates.intentName("AMAZON.PreviousIntent"))
        );
    }

    @Override
    protected Optional<Response> handle(final HandlerInput input, final DeviceSession deviceSession) {
        final String currentToken = SpeechletRequestUtil.getAudioPlayerToken(input.getRequestEnvelope());
        final String previousUrl = deviceSession.getPlaylist().previousOf(currentToken);

        return input.getResponseBuilder()
            .addAudioPlayerPlayDirective(
                PlayBehavior.REPLACE_ALL,
                null,
                null,
                previousUrl,
                previousUrl
            )
            .build();
    }
}
