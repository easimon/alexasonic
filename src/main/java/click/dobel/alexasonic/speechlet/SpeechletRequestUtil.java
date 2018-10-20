package click.dobel.alexasonic.speechlet;

import click.dobel.alexasonic.exception.AlexaSonicException;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;
import com.amazon.ask.model.interfaces.audioplayer.AudioPlayerState;
import com.amazon.ask.model.interfaces.system.SystemState;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Optional;

public final class SpeechletRequestUtil {

    private static final Locale LOCALE_DEFAULT = Locale.ENGLISH;

    public static final String MESSAGE_NOUSERID = "RequestContext.NoUserId";
    public static final String MESSAGE_NODEVICEID = "RequestContext.NoDeviceId";
    public static final String MESSAGEKEY_NOT_PLAYING = "AudioPlayer.NotPlaying";

    private static final String UNKNOWN = "unknown";

    private SpeechletRequestUtil() {
    }

    private static Optional<SystemState> getSystemState(final RequestEnvelope requestEnvelope) {
        return Optional.of(requestEnvelope)
            .map(RequestEnvelope::getContext)
            .map(Context::getSystem);
    }

    private static String shortenId(final String theId) {
        // TODO: are rightmost characters variable enough?
        return StringUtils.right(theId, 7);
    }

    public static String getUserId(final RequestEnvelope requestEnvelope) {
        return getSystemState(requestEnvelope)
            .map(SystemState::getUser)
            .map(User::getUserId)
            .orElseThrow(() -> new AlexaSonicException(MESSAGE_NOUSERID));
    }

    public static String getShortUserId(final RequestEnvelope requestEnvelope) {
        try {
            return shortenId(getUserId(requestEnvelope));
        } catch (final AlexaSonicException ignored) {
            return UNKNOWN;
        }
    }

    public static String getDeviceId(final RequestEnvelope requestEnvelope) {
        return getSystemState(requestEnvelope)
            .map(SystemState::getDevice)
            .map(Device::getDeviceId)
            .orElseThrow(() -> new AlexaSonicException(MESSAGE_NODEVICEID));
    }

    public static String getShortDeviceId(final RequestEnvelope requestEnvelope) {
        try {
            return shortenId(getDeviceId(requestEnvelope));
        } catch (final AlexaSonicException ignored) {
            return UNKNOWN;
        }
    }

    @Nullable
    private static String getLocaleString(final Request request) {
        try {
            // Stupid stupid stupid. Many request types have getLocale(),
            // but there is no common interface or base class
            return (String) request.getClass().getMethod("getLocale").invoke(request);
        } catch (final NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            return null;
        }
    }

    private static Locale getLocale(final RequestEnvelope requestEnvelope) {
        return Optional.of(requestEnvelope)
            .map(RequestEnvelope::getRequest)
            .map(SpeechletRequestUtil::getLocaleString)
            .map(Locale::forLanguageTag)
            .orElse(LOCALE_DEFAULT);
    }

    public static Locale getLocale(final HandlerInput input) {
        return getLocale(input.getRequestEnvelope());
    }

    public static Optional<AudioPlayerState> getAudioPlayerState(final RequestEnvelope requestEnvelope) {
        return Optional.of(requestEnvelope)
            .map(RequestEnvelope::getContext)
            .map(Context::getAudioPlayer);
    }

    public static String getAudioPlayerToken(final RequestEnvelope requestEnvelope) {
        return getAudioPlayerState(requestEnvelope)
            .map(AudioPlayerState::getToken)
            .orElseThrow(() -> new AlexaSonicException(MESSAGEKEY_NOT_PLAYING));
    }

    public static long getAudioPlayerOffsetInMilliseconds(
        final RequestEnvelope requestEnvelope) {
        return getAudioPlayerState(requestEnvelope)
            .map(AudioPlayerState::getOffsetInMilliseconds)
            .orElseThrow(() -> new AlexaSonicException(MESSAGEKEY_NOT_PLAYING));
    }
}
