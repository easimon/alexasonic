package click.dobel.alexasonic.repository;

import click.dobel.alexasonic.domain.playlist.Playlist;
import click.dobel.alexasonic.domain.session.DeviceSession;
import com.amazon.ask.attributes.persistence.PersistenceAdapter;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Context;
import com.amazon.ask.model.Device;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.interfaces.system.SystemState;
import com.greghaskins.spectrum.Spectrum;
import org.junit.runner.RunWith;

import java.util.Optional;

import static com.greghaskins.spectrum.Spectrum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(Spectrum.class)
public class DeviceSessionRepositorySpecTest {

    private PersistenceAdapter adapter;
    private Request request;

    private HandlerInput input(final String deviceId) {
        return HandlerInput.builder()
            .withPersistenceAdapter(adapter)
            .withRequestEnvelope(RequestEnvelope.builder()
                .withRequest(request)
                .withContext(Context.builder()
                    .withSystem(
                        SystemState.builder()
                            .withDevice(Device.builder()
                                .withDeviceId(deviceId)
                                .build())
                            .build()
                    ).build())
                .build())
            .build();
    }


    {
        final DeviceSessionRepository repository = new DeviceSessionRepository();

        beforeEach(() -> {
            adapter = mock(PersistenceAdapter.class);
            when(adapter.getAttributes(any())).thenReturn(Optional.empty());
            request = mock(Request.class);
            when(request.getRequestId()).thenReturn("testRequest");
        });

        describe("getDeviceSession", () -> {
            it("creates a new DeviceSession when none is found.", () -> {
                final String deviceId = "testDeviceId";
                final HandlerInput input = input(deviceId);
                final DeviceSession session = repository.getDeviceSession(input);
                assertThat(session.getDeviceId()).isEqualTo(deviceId);
                assertThat(session.getLastAudioPlayerOffsetInMilliseconds()).isNull();
                assertThat(session.getLastAudioPlayerToken()).isNull();
                assertThat(session.getPlaylist()).isNotNull();
                assertThat(session.getPlaylist().getItems()).isEmpty();
            });

            it("Returns a previously inserted DeviceSession.", () -> {
                final String deviceId = "test2DeviceId";
                final HandlerInput input = input(deviceId);
                final DeviceSession session = new DeviceSession(deviceId);
                session.setPlaylist(new Playlist("a", "b"));

                assertThat(repository.saveDeviceSession(session, input)).isNull();

                final DeviceSession readSession = repository.getDeviceSession(input);
                assertThat(readSession.getDeviceId()).isEqualTo(deviceId);
                assertThat(readSession.getPlaylist().hasItem("a")).isTrue();
                assertThat(readSession.getPlaylist().hasItem("b")).isTrue();
            });
        });
    }
}
