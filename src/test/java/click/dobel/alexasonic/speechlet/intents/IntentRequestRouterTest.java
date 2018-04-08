package click.dobel.alexasonic.speechlet.intents;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;

import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.speechlet.intents.handlers.IntentRequestHandler;
import click.dobel.alexasonic.speechlet.intents.handlers.UnknownIntentRequestHandler;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

public class IntentRequestRouterTest {

    public IntentRequestRouter setupRouter(final IntentRequestHandler... handlers) {
        final IntentRequestRouter result = new IntentRequestRouter(Arrays.asList(handlers));
        for (final IntentRequestHandler handler : handlers) {
            Mockito.reset(handler);
        }
        return result;
    }

    @Test
    public void testRouterEmpty() {
        assertThatNullPointerException() //
                .isThrownBy(() -> setupRouter()) //
                .withMessageContaining("UnknownIntentRequestHandler");
    }

    private IntentRequestHandler createMockIntentRequestHandler(final String name) {
        final IntentRequestHandler handler = Mockito.mock(IntentRequestHandler.class);
        when(handler.getIntentName()).thenReturn(name);
        return handler;
    }

    private IntentRequestHandler createUnknownIntentRequestHandler() {
        return createMockIntentRequestHandler(UnknownIntentRequestHandler.INTENTNAME);
    }

    private RequestContext<IntentRequest> createRequestContext(final String intentName) {
        final DeviceSession session = mock(DeviceSession.class);

        final Intent intent = Intent.builder().withName(intentName).build();
        final IntentRequest intentRequest = IntentRequest.builder().withRequestId("TestRequest").withIntent(intent)
                .build();
        final SpeechletRequestEnvelope<IntentRequest> envelope = SpeechletRequestEnvelope.<IntentRequest>builder()
                .withRequest(intentRequest).build();
        final RequestContext<IntentRequest> context = new RequestContext<>(envelope, session, null);
        return context;
    }

    @Test
    public void testUnknownIntentRequestHandlerOnly() {
        final IntentRequestHandler unknown = createUnknownIntentRequestHandler();
        final IntentRequestRouter router = setupRouter(unknown);
        final RequestContext<IntentRequest> requestContext = createRequestContext("DONTCARE");

        router.route(requestContext);
        verify(unknown).onIntent(Mockito.any());
        verifyNoMoreInteractions(unknown);
    }

    @Test
    public void testDuplicateIntentRequestHandler() {
        final String intentName = "TestIntentName";
        final IntentRequestHandler first = createMockIntentRequestHandler(intentName);
        final IntentRequestHandler second = createMockIntentRequestHandler(intentName);

        assertThatIllegalArgumentException() //
                .isThrownBy(() -> setupRouter(first, second)) //
                .withMessageContaining("Duplicate").withMessageContaining(intentName);
    }

    @Test
    public void testRoute() {
        final String firstIntentName = "FirstIntentName";
        final String secondIntentName = "SecondIntentName";
        final IntentRequestHandler first = createMockIntentRequestHandler(firstIntentName);
        final IntentRequestHandler second = createMockIntentRequestHandler(secondIntentName);
        final IntentRequestHandler unknown = createUnknownIntentRequestHandler();
        final IntentRequestRouter router = setupRouter(first, second, unknown);

        final RequestContext<IntentRequest> context = createRequestContext(firstIntentName);
        router.route(context);

        Mockito.verify(first).onIntent(context);
        Mockito.verifyNoMoreInteractions(first, second, unknown);
    }

}
