package click.dobel.alexasonic.alexa.handlers;

import click.dobel.alexasonic.domain.playlist.Playlist;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.repository.DeviceSessionRepository;
import org.junit.Before;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public abstract class AbstractDeviceSessionAwareRequestHandlerTest extends AbstractHandlerTest {

    protected final Playlist playlist = mock(Playlist.class);
    protected final DeviceSession deviceSession = mock(DeviceSession.class);
    protected final DeviceSessionRepository deviceSessionRepository = mock(DeviceSessionRepository.class);

    @Before
    public void wireDeviceSessionMocks() {
        when(deviceSession.getPlaylist()).thenReturn(playlist);
        when(deviceSessionRepository.getDeviceSession(any())).thenReturn(deviceSession);
    }
}
