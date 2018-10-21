package click.dobel.alexasonic.alexa.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.RequestEnvelope;

public abstract class AbstractHandlerTest {

  protected abstract Request createDefaultTestRequest();

  protected HandlerInput createTestInput() {
    return createTestInput(createDefaultTestRequest());
  }

  protected HandlerInput createTestInput(final Request request) {
    return HandlerInput.builder()
      .withRequestEnvelope(RequestEnvelope.builder()
        .withRequest(request)
        .build())
      .build();
  }
}
