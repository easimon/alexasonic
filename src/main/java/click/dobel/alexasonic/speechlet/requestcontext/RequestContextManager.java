package click.dobel.alexasonic.speechlet.requestcontext;

import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.SpeechletRequest;

import click.dobel.alexasonic.configuration.SubsonicCredentials;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.repository.SessionRepository;
import click.dobel.alexasonic.repository.SubsonicCredentialsRepository;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtils;

@Component
public class RequestContextManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestContextManager.class);

    private static final Locale LOCALE_DEFAULT = Locale.GERMAN;

    private final SubsonicCredentialsRepository credentialsRepository;

    private final SessionRepository sessionRepository;

    @Autowired
    public RequestContextManager(final SubsonicCredentialsRepository credentialsRepository,
            final SessionRepository sessionRepository) {
        this.credentialsRepository = credentialsRepository;
        this.sessionRepository = sessionRepository;
    }

    public static Locale getLocale(final SpeechletRequestEnvelope<? extends SpeechletRequest> requestEnvelope) {
        return Optional.of(requestEnvelope) //
                .map(envelope -> envelope.getRequest()) //
                .map(request -> request.getLocale()) //
                .orElse(LOCALE_DEFAULT);
    }

    private static <T extends SpeechletRequest> DeviceSession updateDeviceSession(final DeviceSession deviceSession,
            final SpeechletRequestEnvelope<T> requestEnvelope) {
        SpeechletRequestUtils.getAudioPlayerState(requestEnvelope).ifPresent(state -> {
            LOGGER.debug("Updating AudioplayerState: T: {}, S: {}", state.getToken(), state.getOffsetInMilliseconds());
            deviceSession.setLastAudioPlayerToken(state.getToken());
            deviceSession.setLastAudioPlayerOffsetInMilliseconds(state.getOffsetInMilliseconds());
        });
        return deviceSession;
    }

    public <T extends SpeechletRequest> RequestContext<T> createContext(final SpeechletRequestEnvelope<T> requestEnvelope) {
        final String userId = SpeechletRequestUtils.getUserId(requestEnvelope);
        final String deviceId = SpeechletRequestUtils.getDeviceId(requestEnvelope);

        final SubsonicCredentials credentials = credentialsRepository.getCredentialsForUser(userId);
        final DeviceSession deviceSession = sessionRepository.getDeviceSession(deviceId);
        if (deviceSession != null) {
            updateDeviceSession(deviceSession, requestEnvelope);
        }

        return new RequestContext<>(requestEnvelope, deviceSession, credentials);
    }

    public void saveContext(final RequestContext<?> context) {
        sessionRepository.saveDeviceSession(context.getDeviceSession());
    }

}
