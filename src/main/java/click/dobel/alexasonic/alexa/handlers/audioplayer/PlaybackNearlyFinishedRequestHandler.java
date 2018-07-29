package click.dobel.alexasonic.alexa.handlers.audioplayer;

import click.dobel.alexasonic.alexa.handlers.AbstractDeviceSessionAwareRequestHandler;
import click.dobel.alexasonic.domain.playlist.Playlist;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.repository.DeviceSessionRepository;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackNearlyFinishedRequest;
import com.amazon.ask.request.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlaybackNearlyFinishedRequestHandler extends AbstractDeviceSessionAwareRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaybackNearlyFinishedRequestHandler.class);

    @Autowired
    public PlaybackNearlyFinishedRequestHandler(final DeviceSessionRepository deviceSessionRepository) {
        super(deviceSessionRepository);
    }

    @Override
    public boolean canHandle(final HandlerInput input) {
        return input.matches(Predicates.requestType(PlaybackNearlyFinishedRequest.class));
    }

    @Override
    public Optional<Response> handle(final HandlerInput input, final DeviceSession deviceSession) {
        final PlaybackNearlyFinishedRequest request = (PlaybackNearlyFinishedRequest) input.getRequestEnvelope().getRequest();

        final String currentToken = request.getToken();
        final Playlist playlist = deviceSession.getPlaylist();

        if (!playlist.hasItem(currentToken)) {
            LOGGER.error("Current Playlist has no item with the token {}.", currentToken);
            return Optional.empty();
        }

        if (!playlist.hasNext(currentToken)) {
            LOGGER.debug("Playlist finished with token {}.", currentToken);
            return Optional.empty();
        }

        final String currentUrl = playlist.get(currentToken);
        final String nextUrl = playlist.nextOf(currentToken);

        return input.getResponseBuilder()
            .addAudioPlayerPlayDirective(
                PlayBehavior.ENQUEUE, null, currentToken, nextUrl, nextUrl)
            .build();
    }
}
