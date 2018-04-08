package click.dobel.alexasonic.speechlet;

import java.util.Optional;
import java.util.function.Function;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioPlayer;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackFailedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackFinishedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackNearlyFinishedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackStartedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackStoppedRequest;

import click.dobel.alexasonic.exception.AlexaSonicException;
import click.dobel.alexasonic.speechlet.audioplayer.PlaybackRequestHandler;
import click.dobel.alexasonic.speechlet.intents.IntentRequestRouter;
import click.dobel.alexasonic.speechlet.intents.handlers.ErrorHandler;
import click.dobel.alexasonic.speechlet.launch.LaunchRequestHandler;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContextManager;

@Component
public class AlexaSonicSpeechlet implements SpeechletV2, AudioPlayer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlexaSonicSpeechlet.class);

    private final LaunchRequestHandler launchRequestHandler;

    private final PlaybackRequestHandler playbackRequestHandler;

    private final IntentRequestRouter intentRequestRouter;

    private final RequestContextManager requestContextManager;

    private final ErrorHandler errorHandler;

    @Autowired
    public AlexaSonicSpeechlet(final LaunchRequestHandler launchRequestHandler,
            final IntentRequestRouter intentRequestRouter, final PlaybackRequestHandler playbackRequestHandler,
            final RequestContextManager requestContextManager, final ErrorHandler errorHandler) {
        this.launchRequestHandler = launchRequestHandler;
        this.intentRequestRouter = intentRequestRouter;
        this.playbackRequestHandler = playbackRequestHandler;
        this.requestContextManager = requestContextManager;
        this.errorHandler = errorHandler;
    }

    private <T extends SpeechletRequest, F extends SpeechletResponse> F nullResponse(final RequestContext<?> context) {
        return null;
    }

    private static String getRequestType(final SpeechletRequestEnvelope<?> requestEnvelope) {
        try {
            return Optional.ofNullable(requestEnvelope) //
                    .map(e -> e.getRequest()) //
                    .map(r -> {
                        if (r instanceof IntentRequest) {
                            return String.format("%s<%s>", //
                                    r.getClass().getSimpleName(), //
                                    Optional.of((IntentRequest) r).map(re -> re.getIntent()).map(i -> i.getName())
                                            .orElse("Unknown"));
                        } else {
                            return r.getClass().getSimpleName();
                        }
                    }) //
                    .orElse("<Unknown request type>");
        } catch (final RuntimeException exception) {
            final String message = "Exception while determining request type name.";
            LOGGER.error(message, exception);
            return String.format("<%s>", message);
        }
    }

    private void prepareMDC(final SpeechletRequestEnvelope<?> requestEnvelope) {
        final String userId = SpeechletRequestUtils.getShortUserId(requestEnvelope);
        final String deviceId = SpeechletRequestUtils.getShortDeviceId(requestEnvelope);
        MDC.put("mdcData", String.format("U: %s, D: %s", //
                userId, //
                deviceId //
        ));
    }

    private <T extends SpeechletRequest, F extends SpeechletResponse> SpeechletResponse wrap(
            final SpeechletRequestEnvelope<T> requestEnvelope, final Function<RequestContext<T>, F> func) {
        prepareMDC(requestEnvelope);
        RequestContext<T> context = null;
        try {
            final String requestType = getRequestType(requestEnvelope);
            context = requestContextManager.createContext(requestEnvelope);
            LOGGER.info("Processing {}.", requestType);
            final F response = func.apply(context);
            requestContextManager.saveContext(context);
            LOGGER.debug("Processed {}.", requestType);
            return response;
        } catch (final AlexaSonicException exception) {
            return errorHandler.onError(context, exception);
        } catch (final RuntimeException exception) {
            LOGGER.error("Unexpected error occurred.", exception);
            return errorHandler.onError(context, ErrorHandler.MESSAGEKEY_ERROR_GENERIC);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void onSessionStarted(final SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        wrap(requestEnvelope, this::nullResponse);
    }

    @Override
    public SpeechletResponse onLaunch(final SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        return wrap(requestEnvelope, launchRequestHandler::onLaunch);
    }

    @Override
    public SpeechletResponse onIntent(final SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        return wrap(requestEnvelope, intentRequestRouter::route);
    }

    @Override
    public void onSessionEnded(final SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        wrap(requestEnvelope, this::nullResponse);
    }

    @Override
    public SpeechletResponse onPlaybackFailed(final SpeechletRequestEnvelope<PlaybackFailedRequest> requestEnvelope) {
        return wrap(requestEnvelope, playbackRequestHandler::onPlaybackFailed);
    }

    @Override
    public SpeechletResponse onPlaybackFinished(
            final SpeechletRequestEnvelope<PlaybackFinishedRequest> requestEnvelope) {
        return wrap(requestEnvelope, playbackRequestHandler::onPlaybackFinished);
    }

    @Override
    public SpeechletResponse onPlaybackNearlyFinished(
            final SpeechletRequestEnvelope<PlaybackNearlyFinishedRequest> requestEnvelope) {
        return wrap(requestEnvelope, playbackRequestHandler::onPlaybackNearlyFinished);
    }

    @Override
    public SpeechletResponse onPlaybackStarted(final SpeechletRequestEnvelope<PlaybackStartedRequest> requestEnvelope) {
        return wrap(requestEnvelope, playbackRequestHandler::onPlaybackStarted);
    }

    @Override
    public SpeechletResponse onPlaybackStopped(final SpeechletRequestEnvelope<PlaybackStoppedRequest> requestEnvelope) {
        return wrap(requestEnvelope, playbackRequestHandler::onPlaybackStopped);
    }

}
