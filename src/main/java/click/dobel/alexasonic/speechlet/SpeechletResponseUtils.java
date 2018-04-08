package click.dobel.alexasonic.speechlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioItem;
import com.amazon.speech.speechlet.interfaces.audioplayer.ClearBehavior;
import com.amazon.speech.speechlet.interfaces.audioplayer.PlayBehavior;
import com.amazon.speech.speechlet.interfaces.audioplayer.Stream;
import com.amazon.speech.speechlet.interfaces.audioplayer.directive.ClearQueueDirective;
import com.amazon.speech.speechlet.interfaces.audioplayer.directive.PlayDirective;
import com.amazon.speech.speechlet.interfaces.audioplayer.directive.StopDirective;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;

public final class SpeechletResponseUtils {

    private static List<Directive> ensureDirectives(final SpeechletResponse response) {
        Objects.requireNonNull(response, "Response may not be null.");
        return Optional.ofNullable(response.getDirectives()) //
                .orElseGet(() -> {
                    final List<Directive> result = new ArrayList<>();
                    response.setDirectives(result);
                    return result;
                });
    }

    public static SpeechletResponse addDirective(final SpeechletResponse response, final Directive directive) {
        ensureDirectives(response).add(directive);
        return response;
    }

    private static PlayDirective createPlayDirective(final String url, final String previousUrl,
            final PlayBehavior playBehaviour) {
        return createPlayDirective(url, previousUrl, null, playBehaviour);
    }

    private static PlayDirective createPlayDirective(final String url, final String previousUrl,
            final Long offsetInMilliseconds, final PlayBehavior playBehaviour) {
        final Stream stream = new Stream();
        stream.setUrl(url);
        stream.setToken(url);
        if (PlayBehavior.ENQUEUE.equals(playBehaviour) && previousUrl != null) {
            stream.setExpectedPreviousToken(previousUrl);
        }
        if (offsetInMilliseconds != null && offsetInMilliseconds > 0) {
            stream.setOffsetInMilliseconds(offsetInMilliseconds);
        }

        final AudioItem audioItem = new AudioItem();
        audioItem.setStream(stream);
        final PlayDirective directive = new PlayDirective();
        directive.setAudioItem(audioItem);
        directive.setPlayBehavior(playBehaviour);
        return directive;
    }

    public static SpeechletResponse addPlayDirective(final SpeechletResponse response, final String url,
            final PlayBehavior playBehaviour) {
        return addPlayDirective(response, url, null, playBehaviour);
    }

    public static SpeechletResponse addPlayDirective(final SpeechletResponse response, final String url,
            final String previousUrl, final PlayBehavior playBehaviour) {
        return addDirective(response, createPlayDirective(url, previousUrl, playBehaviour));
    }

    private static SpeechletResponse addPlayDirective(final SpeechletResponse response, final Long offsetInMilliseconds,
            final String url, final String previousUrl, final PlayBehavior playBehaviour) {
        return addDirective(response, createPlayDirective(url, previousUrl, offsetInMilliseconds, playBehaviour));
    }

    public static SpeechletResponse newDirective(final Directive directive) {
        return addDirective(new SpeechletResponse(), directive);
    }

    public static SpeechletResponse newPlayDirective(final String url, final PlayBehavior playBehaviour) {
        return newPlayDirective(url, (String) null, playBehaviour);
    }

    public static SpeechletResponse newPlayDirective(final String url, final String previousUrl,
            final PlayBehavior playBehaviour) {
        return addPlayDirective(new SpeechletResponse(), url, previousUrl, playBehaviour);
    }

    public static SpeechletResponse newPlayDirective(final String url, final String previousUrl,
            final Long offsetInMilliseconds, final PlayBehavior playBehaviour) {
        return addPlayDirective(new SpeechletResponse(), offsetInMilliseconds, url, previousUrl, playBehaviour);
    }

    public static SpeechletResponse newPlayDirective(final String url, final Long offsetInMilliseconds,
            final PlayBehavior playBehaviour) {
        return newPlayDirective(url, null, offsetInMilliseconds, playBehaviour);
    }

    public static SpeechletResponse newStopDirective() {
        return addDirective(new SpeechletResponse(), new StopDirective());
    }

    public static SpeechletResponse newClearQueueDirective(final ClearBehavior clearBehavior) {
        final ClearQueueDirective directive = new ClearQueueDirective();
        directive.setClearBehavior(clearBehavior);
        return addDirective(new SpeechletResponse(), directive);
    }

    public static SpeechletResponse newPlaintextTellResponse(final String message) {
        final PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(message);
        final SpeechletResponse response = SpeechletResponse.newTellResponse(outputSpeech);
        return response;
    }

    public static SpeechletResponse newPlaintextAskResponse(final String message, final String repromptMessage) {
        final PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(message);

        final PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptMessage);

        final Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        final SpeechletResponse response = SpeechletResponse.newAskResponse(outputSpeech, reprompt);
        return response;
    }

}
