package click.dobel.alexasonic.alexa.handlers.lifecycle;

import click.dobel.alexasonic.i18n.Messages;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class LaunchRequestHandler implements RequestHandler {

  private static final String MESSAGEKEY_LAUNCH_GREETING = "LaunchRequest.Greeting";
  private static final String MESSAGEKEY_LAUNCH_REPROMPT = "LaunchRequest.Reprompt";

  private final Messages messages;

  public LaunchRequestHandler(final Messages messages) {
    this.messages = messages;
  }

  @Override
  public boolean canHandle(final HandlerInput input) {
    return input.matches(Predicates.requestType(LaunchRequest.class));
  }

  @Override
  public Optional<Response> handle(final HandlerInput input) {
    final Locale locale = SpeechletRequestUtil.getLocale(input);

    final String message = messages.getMessage(MESSAGEKEY_LAUNCH_GREETING, locale);
    final String reprompt = messages.getMessage(MESSAGEKEY_LAUNCH_REPROMPT, locale);

    return input.getResponseBuilder()
      .withSpeech(message)
      .withReprompt(reprompt)
      .build();
  }
}
