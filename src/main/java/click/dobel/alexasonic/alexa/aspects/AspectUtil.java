package click.dobel.alexasonic.alexa.aspects;

import org.aspectj.lang.JoinPoint;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AspectUtil {

  private AspectUtil() {
  }

  public static Logger logger(@NotNull final JoinPoint joinPoint) {
    return LoggerFactory.getLogger(joinPoint.getTarget().getClass());
  }
}
