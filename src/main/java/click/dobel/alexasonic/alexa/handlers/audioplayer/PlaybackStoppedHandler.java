package click.dobel.alexasonic.alexa.handlers.audioplayer;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackStoppedRequest;
import com.amazon.ask.request.Predicates;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlaybackStoppedHandler implements RequestHandler {

  @Override
  public boolean canHandle(final HandlerInput input) {
    return input.matches(Predicates.requestType(PlaybackStoppedRequest.class));
  }

  @Override
  public Optional<Response> handle(final HandlerInput input) {
    return Optional.empty();
  }
}
