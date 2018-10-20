package click.dobel.alexasonic.alexa.handlers.audioplayer;

import click.dobel.alexasonic.alexa.handlers.AbstractHandlerTest;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackStoppedRequest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlaybackStoppedHandlerTest extends AbstractHandlerTest {

    private final RequestHandler handler = new PlaybackStoppedHandler();

    @Test
    public void canHandlePlaybackStopped() {
        assertThat(handler.canHandle(createTestInput())).isTrue();
    }

    @Test
    public void handle() {
        assertThat(handler.handle(createTestInput())).isEmpty();
    }

    @Override
    protected Request createDefaultTestRequest() {
        return PlaybackStoppedRequest.builder().build();
    }
}
