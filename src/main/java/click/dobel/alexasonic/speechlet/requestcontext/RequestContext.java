package click.dobel.alexasonic.speechlet.requestcontext;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.SpeechletRequest;

import click.dobel.alexasonic.configuration.SubsonicCredentials;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.exception.Optionals;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtils;

public class RequestContext<T extends SpeechletRequest> {

    public static final String MESSAGE_PREFIX = "RequestContext";
    public static final String MESSAGE_NOSUBSONIC_CREDENTIALS = MESSAGE_PREFIX + ".NoSubsonicCredentials";
    public static final String MESSAGE_NOUSERID = MESSAGE_PREFIX + ".NoUserId";
    public static final String MESSAGE_NODEVICEID = MESSAGE_PREFIX + ".NoDeviceId";
    public static final String MESSAGE_NOPLAYLIST = MESSAGE_PREFIX + ".NoPlaylist";

    private final SpeechletRequestEnvelope<T> requestEnvelope;
    private final Optional<SubsonicCredentials> subsonicCredentials;
    private final DeviceSession deviceSession;

    public RequestContext(final SpeechletRequestEnvelope<T> requestEnvelope, final DeviceSession deviceSession,
            final SubsonicCredentials subsonicCredentials) {
        Objects.requireNonNull(requestEnvelope, "RequestEnvelope may not be null");
        Objects.requireNonNull(deviceSession, "DeviceSession may not be null");

        this.requestEnvelope = requestEnvelope;
        this.deviceSession = deviceSession;
        this.subsonicCredentials = Optional.ofNullable(subsonicCredentials);
    }

    /**
     * Returns the request envelope (never null).
     *
     * @return the request envelope.
     */

    public SpeechletRequestEnvelope<T> getRequestEnvelope() {
        return requestEnvelope;
    }

    public T getRequest() {
        return requestEnvelope.getRequest();
    }

    /**
     * Returns the device session (never null).
     *
     * @return the device session.
     */
    public DeviceSession getDeviceSession() {
        return deviceSession;
    }

    public Optional<SubsonicCredentials> getSubsonicCredentials() {
        return subsonicCredentials;
    }

    public SubsonicCredentials requireSubsonicCredentials() {
        return Optionals.require(getSubsonicCredentials(), MESSAGE_NOSUBSONIC_CREDENTIALS);
    }

    public Locale getLocale() {
        return SpeechletRequestUtils.getLocale(requestEnvelope);
    }

    public String getUserId() {
        return SpeechletRequestUtils.getUserId(requestEnvelope);
    }

    public String getDeviceId() {
        return SpeechletRequestUtils.getDeviceId(requestEnvelope);
    }

}
