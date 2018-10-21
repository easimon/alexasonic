package click.dobel.alexasonic.alexa.handlers.audioplayer;

import click.dobel.alexasonic.alexa.handlers.AbstractDeviceSessionAwareRequestHandlerTest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.interfaces.audioplayer.Error;
import com.amazon.ask.model.interfaces.audioplayer.ErrorType;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackFailedRequest;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PlaybackFailedHandlerTest extends AbstractDeviceSessionAwareRequestHandlerTest {

  private PlaybackFailedHandler handler;

  @Before
  public void initHandler() {
    handler = new PlaybackFailedHandler(deviceSessionRepository);
  }

  @Test
  public void canHandlePlaybackFailed() {
    assertThat(handler.canHandle(createTestInput())).isTrue();
  }

  @Test
  public void handle() {
    assertThat(handler.handle(createTestInput())).isEmpty();
    verify(playlist, times(1)).clear();
  }

  @Override
  protected Request createDefaultTestRequest() {
    return PlaybackFailedRequest.builder()
      .withError(Error.builder()
        .withMessage("TestMessage")
        .withType(ErrorType.MEDIA_ERROR_INTERNAL_DEVICE_ERROR)
        .build())
      .build();
  }
}
