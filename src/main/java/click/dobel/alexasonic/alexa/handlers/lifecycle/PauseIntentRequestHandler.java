package click.dobel.alexasonic.alexa.handlers.lifecycle;

import click.dobel.alexasonic.alexa.handlers.AbstractDeviceSessionAwareRequestHandler;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.repository.DeviceSessionRepository;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.playbackcontroller.PauseCommandIssuedRequest;
import com.amazon.ask.request.Predicates;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PauseIntentRequestHandler extends AbstractDeviceSessionAwareRequestHandler {

    public PauseIntentRequestHandler(final DeviceSessionRepository deviceSessionRepository) {
        super(deviceSessionRepository);
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(
            Predicates.requestType(PauseCommandIssuedRequest.class)
                .or(Predicates.intentName("AMAZON.PauseIntent"))
        );
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final DeviceSession session) {

        SpeechletRequestUtil.getAudioPlayerState(input.getRequestEnvelope()).ifPresent(state -> {
            state.getOffsetInMilliseconds();
            state.getPlayerActivity();
        });

        SpeechletRequestUtil.getAudioPlayerToken(input.getRequestEnvelope());
        SpeechletRequestUtil.getAudioPlayerOffsetInMilliseconds(input.getRequestEnvelope());
        return input.getResponseBuilder()
            .addAudioPlayerStopDirective()
            .build();
    }
}
