package click.dobel.alexasonic.alexa.aspects;

import click.dobel.alexasonic.test.AbstractAlexaSonicSpringTest;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Context;
import com.amazon.ask.model.Device;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.User;
import com.amazon.ask.model.interfaces.system.SystemState;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Ignore("No assertions, only for local verification of MDC/logging aspect.")
public class AspectLoggingTest extends AbstractAlexaSonicSpringTest {

    @Autowired
    private List<RequestHandler> requestHandlers;

    private RequestHandler someHandler;

    private static final String userId = "1234567";
    private static final String deviceId = "7654321";

    @Before
    public void findAnyHandler() {
        someHandler = requestHandlers.get(0);
    }

    @Test
    public void canHandleWithUserIdAndDeviceId() {
        someHandler.canHandle(HandlerInput.builder()
            .withRequestEnvelope(
                RequestEnvelope.builder()
                    .withContext(Context.builder()
                        .withSystem(SystemState.builder()
                            .withUser(User.builder()
                                .withUserId(userId)
                                .build())
                            .withDevice(Device.builder()
                                .withDeviceId(deviceId)
                                .build())
                            .build())
                        .build())
                    .build())
            .build());
    }

    @Test
    public void canHandleWithUserId() {
        someHandler.canHandle(HandlerInput.builder()
            .withRequestEnvelope(
                RequestEnvelope.builder()
                    .withContext(Context.builder()
                        .withSystem(SystemState.builder()
                            .withUser(User.builder()
                                .withUserId(userId)
                                .build())
                            .build())
                        .build())
                    .build())
            .build());
    }

    @Test
    public void canHandleWithDeviceId() {
        someHandler.canHandle(HandlerInput.builder()
            .withRequestEnvelope(
                RequestEnvelope.builder()
                    .withContext(Context.builder()
                        .withSystem(SystemState.builder()
                            .withDevice(Device.builder()
                                .withDeviceId(deviceId)
                                .build())
                            .build())
                        .build())
                    .build())
            .build());
    }

    @Test
    public void canHandleWithNoId() {
        someHandler.canHandle(HandlerInput.builder()
            .withRequestEnvelope(
                RequestEnvelope.builder()
                    .build())
            .build());
    }
}