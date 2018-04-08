package click.dobel.alexasonic.speechlet.audioplayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.interfaces.audioplayer.ErrorType;
import com.amazon.speech.speechlet.interfaces.audioplayer.PlayBehavior;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackFailedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackFinishedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackNearlyFinishedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackStartedRequest;
import com.amazon.speech.speechlet.interfaces.audioplayer.request.PlaybackStoppedRequest;

import click.dobel.alexasonic.domain.playlist.Playlist;
import click.dobel.alexasonic.speechlet.SpeechletResponseUtils;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@Component
public class PlaybackRequestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaybackRequestHandler.class);

    public SpeechletResponse onPlaybackFailed(final RequestContext<PlaybackFailedRequest> requestContext) {
        requestContext.getDeviceSession().getPlaylist().clear();

        final PlaybackFailedRequest request = requestContext.getRequest();
        final String error = request.getError().getMessage();
        final ErrorType type = request.getError().getType();

        LOGGER.warn("Playback of item failed: {},{}. Playlist cleared.", type, error);
        return null;
    }

    public SpeechletResponse onPlaybackFinished(final RequestContext<PlaybackFinishedRequest> requestContext) {
        // requestContext.getDeviceSession().getPlaylist().clear();
        return null;
    }

    public SpeechletResponse onPlaybackNearlyFinished(
            final RequestContext<PlaybackNearlyFinishedRequest> requestContext) {

        final PlaybackNearlyFinishedRequest request = requestContext.getRequest();
        final String currentToken = request.getToken();

        final Playlist playlist = requestContext.getDeviceSession().getPlaylist();

        if (!playlist.hasItem(currentToken)) {
            LOGGER.error("Current Playlist has no item with the token {}.", currentToken);
            return null;
        }

        if (!playlist.hasNext(currentToken)) {
            LOGGER.debug("Playlist finished with token {}.", currentToken);
            return null;
        }

        final String currentUrl = playlist.get(currentToken);
        final String nextUrl = playlist.nextOf(currentToken);

        return SpeechletResponseUtils.newPlayDirective(nextUrl, currentUrl, PlayBehavior.ENQUEUE);
    }

    public SpeechletResponse onPlaybackStarted(final RequestContext<PlaybackStartedRequest> requestContext) {
        return null;
    }

    public SpeechletResponse onPlaybackStopped(final RequestContext<PlaybackStoppedRequest> requestContext) {
        return null;
    }

}
