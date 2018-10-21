package click.dobel.alexasonic.alexa.handlers.lifecycle;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;
import com.amazon.ask.request.Predicates;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SessionEndedRequestHandler implements RequestHandler {

  @Override
  public boolean canHandle(final HandlerInput input) {
    return input.matches(Predicates.requestType(SessionEndedRequest.class));
  }

  @Override
  public Optional<Response> handle(final HandlerInput input) {
    return Optional.empty();
  }
}
