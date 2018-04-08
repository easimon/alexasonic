package click.dobel.alexasonic.speechlet;

import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.SpeechletRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioPlayerInterface;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioPlayerState;
import com.amazon.speech.speechlet.interfaces.system.SystemInterface;
import com.amazon.speech.speechlet.interfaces.system.SystemState;

import click.dobel.alexasonic.exception.AlexaSonicException;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

public final class SpeechletRequestUtils {

    private static final Locale LOCALE_DEFAULT = Locale.ENGLISH;

    private static final String MESSAGE_NOUSERID = RequestContext.MESSAGE_NOUSERID;
    private static final String MESSAGE_NODEVICEID = RequestContext.MESSAGE_NODEVICEID;
    public static final String MESSAGEKEY_NOT_PLAYING = "AudioPlayer.NotPlaying";

    private static Optional<SystemState> getSystemState(final SpeechletRequestEnvelope<?> requestEnvelope) {
        return Optional.of(requestEnvelope) //
                .map(envelope -> envelope.getContext()) //
                .map(context -> context.getState(SystemInterface.class, SystemState.class));
    }

    private static String shortenId(final String id) {
        // TODO: are rightmost characters variable enough?
        return StringUtils.right(id, 8);
    }

    public static String getUserId(final SpeechletRequestEnvelope<?> requestEnvelope) {
        return getSystemState(requestEnvelope) //
                .map(system -> system.getUser()) //
                .map(user -> user.getUserId()) //
                .orElseThrow(() -> new AlexaSonicException(MESSAGE_NOUSERID));
    }

    public static String getShortUserId(final SpeechletRequestEnvelope<?> requestEnvelope) {
        return shortenId(getUserId(requestEnvelope));
    }

    public static String getDeviceId(final SpeechletRequestEnvelope<?> requestEnvelope) {
        return getSystemState(requestEnvelope) //
                .map(system -> system.getDevice()) //
                .map(device -> device.getDeviceId()).orElseThrow(() -> new AlexaSonicException(MESSAGE_NODEVICEID));
    }

    public static String getShortDeviceId(final SpeechletRequestEnvelope<?> requestEnvelope) {
        return shortenId(getDeviceId(requestEnvelope));
    }

    public static Locale getLocale(final SpeechletRequestEnvelope<?> requestEnvelope) {
        return Optional.of(requestEnvelope) //
                .map(envelope -> envelope.getRequest()) //
                .map(request -> request.getLocale()) //
                .orElse(LOCALE_DEFAULT);
    }

    public static Optional<AudioPlayerState> getAudioPlayerState(
            final SpeechletRequestEnvelope<? extends SpeechletRequest> requestEnvelope) {
        return Optional.of(requestEnvelope) //
                .map(e -> e.getContext()) //
                .map(c -> c.getState(AudioPlayerInterface.class, AudioPlayerState.class));
    }

    public static String getAudioPlayerToken(
            final SpeechletRequestEnvelope<? extends SpeechletRequest> requestEnvelope) {
        return getAudioPlayerState(requestEnvelope).map(s -> s.getToken()) //
                .orElseThrow(() -> new AlexaSonicException(MESSAGEKEY_NOT_PLAYING));
    }

    public static long getAudioPlayerOffsetInMilliseconds(
            final SpeechletRequestEnvelope<? extends SpeechletRequest> requestEnvelope) {
        return getAudioPlayerState(requestEnvelope).map(s -> s.getOffsetInMilliseconds()) //
                .orElseThrow(() -> new AlexaSonicException(MESSAGEKEY_NOT_PLAYING));
    }

}
