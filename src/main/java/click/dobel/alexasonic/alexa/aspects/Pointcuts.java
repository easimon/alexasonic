package click.dobel.alexasonic.alexa.aspects;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class Pointcuts {

  @Pointcut("within(@(@org.springframework.stereotype.Component *) *)")
  public void springComponentSpecialization() { /* does not work */
  }

  @Pointcut("within(@org.springframework.stereotype.Component *)")
  public void springComponent() {
  }

  @Pointcut("within(com.amazon.ask.dispatcher.request.handler.RequestHandler+)")
  public void requestHandler() {
  }

  @Pointcut("within(com.amazon.ask.dispatcher.exception.ExceptionHandler+)")
  public void errorHandler() {
  }

  @Pointcut("execution(boolean *.canHandle(..))")
  public void canHandleInvocation() {
  }

  @Pointcut("execution(java.util.Optional<com.amazon.ask.model.Response> *.handle(..))")
  public void handleInvocation() {
  }
}
