package click.dobel.alexasonic.alexa.handlers.lifecycle;

import click.dobel.alexasonic.alexa.handlers.AbstractDeviceSessionAwareRequestHandler;
import click.dobel.alexasonic.domain.playlist.Playlist;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.exception.AlexaSonicException;
import click.dobel.alexasonic.repository.DeviceSessionRepository;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.request.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResumeIntentRequestHandler extends AbstractDeviceSessionAwareRequestHandler {

    private static final String MESSAGEKEY_NOT_PLAYING = SpeechletRequestUtil.MESSAGEKEY_NOT_PLAYING;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResumeIntentRequestHandler.class);

    @Autowired
    public ResumeIntentRequestHandler(final DeviceSessionRepository deviceSessionRepository) {
        super(deviceSessionRepository);
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(Predicates.intentName("AMAZON.ResumeIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final DeviceSession deviceSession) {
        final String token = deviceSession.getLastAudioPlayerToken();
        if (token == null) {
            throw new AlexaSonicException(MESSAGEKEY_NOT_PLAYING);
        }

        final Playlist playlist = deviceSession.getPlaylist();
        final String url = playlist.get(token);
        final Long offsetInMilliseconds = deviceSession.getLastAudioPlayerOffsetInMilliseconds();

        LOGGER.debug("Received resume request for token {}.", token);

        return input.getResponseBuilder()
            .addAudioPlayerPlayDirective(
                PlayBehavior.REPLACE_ALL,
                offsetInMilliseconds,
                null,
                url,
                url
            )
            .build();
    }
}
