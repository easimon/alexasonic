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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StartOverIntentRequestHandler extends AbstractDeviceSessionAwareRequestHandler {

    private static final String MESSAGEKEY_NOT_PLAYING = SpeechletRequestUtil.MESSAGEKEY_NOT_PLAYING;

    @Autowired
    public StartOverIntentRequestHandler(final DeviceSessionRepository deviceSessionRepository) {
        super(deviceSessionRepository);
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(Predicates.intentName("AMAZON.StartOverIntent"));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final DeviceSession deviceSession) {
        final String token = deviceSession.getLastAudioPlayerToken();
        if (token == null) {
            throw new AlexaSonicException(MESSAGEKEY_NOT_PLAYING);
        }

        final Playlist playlist = deviceSession.getPlaylist();
        final String url = playlist.get(token);

        return input.getResponseBuilder()
            .addAudioPlayerPlayDirective(
                PlayBehavior.REPLACE_ALL,
                0L,
                null,
                url,
                url
            )
            .build();
    }
}
