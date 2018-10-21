package click.dobel.alexasonic.alexa.handlers.audioplayer;

import click.dobel.alexasonic.alexa.handlers.AbstractDeviceSessionAwareRequestHandler;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.repository.DeviceSessionRepository;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.ErrorType;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackFailedRequest;
import com.amazon.ask.request.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlaybackFailedHandler extends AbstractDeviceSessionAwareRequestHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(PlaybackFailedHandler.class);

  @Autowired
  public PlaybackFailedHandler(final DeviceSessionRepository deviceSessionRepository) {
    super(deviceSessionRepository);
  }

  @Override
  public boolean canHandle(final HandlerInput input) {
    return input.matches(Predicates.requestType(PlaybackFailedRequest.class));
  }

  @Override
  protected Optional<Response> handle(final HandlerInput input, final DeviceSession deviceSession) {
    deviceSession.getPlaylist().clear();

    final PlaybackFailedRequest request = (PlaybackFailedRequest) input.getRequestEnvelope().getRequest();
    final String error = request.getError().getMessage();
    final ErrorType type = request.getError().getType();

    LOGGER.warn("Playback of item failed: {},{}. Playlist cleared.", type, error);
    return Optional.empty();
  }
}
