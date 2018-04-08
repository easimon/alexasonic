package click.dobel.alexasonic.domain.session;

import java.util.Objects;

import click.dobel.alexasonic.domain.playlist.Playlist;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "devicesession", schemaVersion = "1.0")
public class DeviceSession {

    @Id
    private String deviceId;

    private String lastAudioPlayerToken = null;
    private Long lastAudioPlayerOffsetInMilliseconds = null;
    private Playlist playlist = null;

    public DeviceSession() {
        this.deviceId = "uninitialized";
    }

    public DeviceSession(final String deviceId) {
        Objects.requireNonNull(deviceId, "DeviceId may not be null.");
        this.deviceId = deviceId;
        this.playlist = new Playlist();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(final String deviceId) {
        Objects.requireNonNull(deviceId, "DeviceId may not be null.");
        this.deviceId = deviceId;
    }

    public String getLastAudioPlayerToken() {
        return lastAudioPlayerToken;
    }

    public void setLastAudioPlayerToken(final String lastAudioPlayerToken) {
        this.lastAudioPlayerToken = lastAudioPlayerToken;
    }

    public Long getLastAudioPlayerOffsetInMilliseconds() {
        return lastAudioPlayerOffsetInMilliseconds;
    }

    public void setLastAudioPlayerOffsetInMilliseconds(final Long lastAudioPlayerOffsetInMilliseconds) {
        this.lastAudioPlayerOffsetInMilliseconds = lastAudioPlayerOffsetInMilliseconds;
    }

    /**
     * Returns the current playlist. Never null, but may be empty.
     *
     * @return the current playlist
     */
    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(final Playlist playlist) {
        Objects.requireNonNull(playlist, "Playlist may not be null.");
        this.playlist = playlist;
    }

}
