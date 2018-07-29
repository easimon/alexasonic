package click.dobel.alexasonic.domain.session;

import click.dobel.alexasonic.domain.playlist.Playlist;

import java.io.Serializable;
import java.util.Objects;

public class DeviceSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private String deviceId;

    private String lastAudioPlayerToken;
    private Long lastAudioPlayerOffsetInMilliseconds;
    private Playlist playlist;

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
