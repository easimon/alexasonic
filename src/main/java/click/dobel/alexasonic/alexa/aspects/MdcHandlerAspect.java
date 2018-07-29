package click.dobel.alexasonic.alexa.aspects;

import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static click.dobel.alexasonic.alexa.aspects.AspectUtil.logger;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class MdcHandlerAspect {

    @Pointcut("(" +
        "       (Pointcuts.handleInvocation() || Pointcuts.canHandleInvocation()) " +
        "    && (Pointcuts.requestHandler() || Pointcuts.errorHandler())" +
        ")")
    public void mdc() {
    }

    @Before("mdc() && args(input)")
    public void setMdc(final JoinPoint jp, final HandlerInput input) {
        final RequestEnvelope envelope = input.getRequestEnvelope();
        final String userId = SpeechletRequestUtil.getShortUserId(envelope);
        final String deviceId = SpeechletRequestUtil.getShortDeviceId(envelope);

        MDC.put("mdcData", String.format("u: %s, d: %s", userId, deviceId));
        logger(jp).trace("MDC created.");
    }

    @After("mdc()")
    public void clearMdc(final JoinPoint jp) {
        logger(jp).trace("Clearing MDC.");
        MDC.clear();
    }
}
