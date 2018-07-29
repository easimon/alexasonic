package click.dobel.alexasonic.alexa.handlers.lifecycle;

import click.dobel.alexasonic.alexa.handlers.AbstractDeviceSessionAwareRequestHandler;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.repository.DeviceSessionRepository;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.model.interfaces.playbackcontroller.NextCommandIssuedRequest;
import com.amazon.ask.request.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NextIntentRequestHandler extends AbstractDeviceSessionAwareRequestHandler {

    @Autowired
    public NextIntentRequestHandler(final DeviceSessionRepository deviceSessionRepository) {
        super(deviceSessionRepository);
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(
            Predicates.requestType(NextCommandIssuedRequest.class)
                .or(Predicates.intentName("AMAZON.NextIntent"))
        );
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final DeviceSession deviceSession) {
        final String currentToken = SpeechletRequestUtil.getAudioPlayerToken(input.getRequestEnvelope());
        final String next = deviceSession.getPlaylist().nextOf(currentToken);

        return input.getResponseBuilder()
            .addAudioPlayerPlayDirective(
                PlayBehavior.REPLACE_ALL,
                null,
                currentToken,
                next,
                next
            )
            .build();
    }
}
