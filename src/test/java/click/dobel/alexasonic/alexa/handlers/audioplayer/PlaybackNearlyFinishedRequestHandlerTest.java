package click.dobel.alexasonic.alexa.handlers.audioplayer;

import click.dobel.alexasonic.alexa.handlers.AbstractDeviceSessionAwareRequestHandlerTest;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.AudioItem;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.model.interfaces.audioplayer.PlayDirective;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackNearlyFinishedRequest;
import com.amazon.ask.model.interfaces.audioplayer.Stream;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PlaybackNearlyFinishedRequestHandlerTest extends AbstractDeviceSessionAwareRequestHandlerTest {

  private RequestHandler handler;

  @Before
  public void initHandler() {
    handler = new PlaybackNearlyFinishedRequestHandler(deviceSessionRepository);
  }

  @Test
  public void canHandlePlaybackNearlyFinished() {
    assertThat(handler.canHandle(createTestInput()));
  }

  @Test
  public void handleInvalidToken() {
    when(playlist.hasItem(any())).thenReturn(false);
    assertThat(handler.handle(createTestInput())).isEmpty();
  }

  @Test
  public void handleNoNextSong() {
    when(playlist.hasItem(any())).thenReturn(true);
    when(playlist.hasNext(any())).thenReturn(false);
    assertThat(handler.handle(createTestInput())).isEmpty();
  }

  private static final String CURRENT_URL = "current";
  private static final String CURRENT_TOKEN = CURRENT_URL;
  private static final String NEXT_URL = "next";
  private static final String NEXT_TOKEN = NEXT_URL;

  @Test
  public void handleEnqueuesNextSong() {
    when(playlist.hasItem(any())).thenReturn(true);
    when(playlist.hasNext(any())).thenReturn(true);
    when(playlist.get(CURRENT_TOKEN)).thenReturn(CURRENT_URL);
    when(playlist.nextOf(CURRENT_TOKEN)).thenReturn(NEXT_URL);

    final Optional<Response> response = handler.handle(createTestInput());
    assertThat(response).isNotEmpty();
    assertThat(response.get().getDirectives()).contains(
      PlayDirective.builder()
        .withPlayBehavior(PlayBehavior.ENQUEUE)
        .withAudioItem(AudioItem.builder()
          .withStream(Stream.builder()
            .withExpectedPreviousToken(CURRENT_TOKEN)
            .withToken(NEXT_TOKEN)
            .withUrl(NEXT_URL)
            .build())
          .build())
        .build()
    );
  }

  @Override
  protected Request createDefaultTestRequest() {
    return PlaybackNearlyFinishedRequest.builder()
      .withToken(CURRENT_TOKEN)
      .build();
  }
}
