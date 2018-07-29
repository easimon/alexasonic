package click.dobel.alexasonic.alexa.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static click.dobel.alexasonic.alexa.aspects.AspectUtil.logger;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("(" +
        "       (Pointcuts.canHandleInvocation()) " +
        "    && (Pointcuts.requestHandler() || Pointcuts.errorHandler())" +
        ")")
    public void canHandle() {
    }

    @Pointcut("(" +
        "       (Pointcuts.handleInvocation()) " +
        "    && (Pointcuts.requestHandler() || Pointcuts.errorHandler())" +
        ")")
    public void handle() {
    }

    @Before("canHandle()")
    public void logCanHandleBeforeInvocation(final JoinPoint jp) {
        logger(jp).debug("invoking canHandle()");
    }

    @After("canHandle()")
    public void logCanHandleAfterInvocation(final JoinPoint jp) {
        logger(jp).debug("invoked canHandle()", (Object) jp.getArgs());
    }

    @Before("handle()")
    public void logHandleBeforeInvocation(final JoinPoint jp) {
        logger(jp).info("invoking handle()");
    }

    @After("handle()")
    public void logHandleAfterInvocation(final JoinPoint jp) {
        logger(jp).debug("invoked handle()");
    }
}
