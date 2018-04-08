package click.dobel.alexasonic.speechlet.intents.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.interfaces.audioplayer.PlayBehavior;

import click.dobel.alexasonic.domain.playlist.Playlist;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.exception.AlexaSonicException;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtils;
import click.dobel.alexasonic.speechlet.SpeechletResponseUtils;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@Component
public class ResumeIntentRequestHandler implements IntentRequestHandler {

    private static final String MESSAGEKEY_NOT_PLAYING = SpeechletRequestUtils.MESSAGEKEY_NOT_PLAYING;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResumeIntentRequestHandler.class);

    private static final String INTENTNAME = "AMAZON.ResumeIntent";

    @Override
    public String getIntentName() {
        return INTENTNAME;
    }

    @Override
    public SpeechletResponse onIntent(final RequestContext<IntentRequest> context) {
        final DeviceSession session = context.getDeviceSession();
        final Playlist playlist = session.getPlaylist();

        final String token = session.getLastAudioPlayerToken();

        if (token == null) {
            throw new AlexaSonicException(MESSAGEKEY_NOT_PLAYING);
        }

        final String url = playlist.get(token);
        final Long offsetInMilliseconds = session.getLastAudioPlayerOffsetInMilliseconds();

        LOGGER.debug("Received resume request for token {}.", token);

        return SpeechletResponseUtils.newPlayDirective(url, offsetInMilliseconds, PlayBehavior.REPLACE_ALL);
    }

}
