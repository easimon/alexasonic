package click.dobel.alexasonic.speechlet.intents.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.subsonic.restapi.Child;
import org.subsonic.restapi.Songs;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.interfaces.audioplayer.PlayBehavior;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

import click.dobel.alexasonic.configuration.SubsonicCredentials;
import click.dobel.alexasonic.domain.playlist.Playlist;
import click.dobel.alexasonic.i18n.Messages;
import click.dobel.alexasonic.restclient.SubsonicRestClient;
import click.dobel.alexasonic.restclient.requestbuilders.GetRandomSongsRequestBuilder;
import click.dobel.alexasonic.restclient.requestbuilders.StreamRequestBuilder;
import click.dobel.alexasonic.restclient.responseconverters.FlatteningSubsonicResponseConverter;
import click.dobel.alexasonic.restclient.responseconverters.ResponseConverters;
import click.dobel.alexasonic.speechlet.SpeechletResponseUtils;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@Component
public class PlayRandomSongsIntentRequestHandler implements IntentRequestHandler {

    public static final String INTENTNAME = "PlayRandomSongs";

    private static final String MESSAGEKEY_START = INTENTNAME + ".Start";
    private static final String MESSAGEKEY_NO_SONGS = INTENTNAME + ".NoSongs";
    private static final String MESSAGEKEY_CARD_TITLE = INTENTNAME + ".CardTitle";

    private final SubsonicRestClient subsonicRestClient;

    private final ErrorHandler errorHandler;

    private final Messages messages;

    @Autowired
    public PlayRandomSongsIntentRequestHandler(final SubsonicRestClient subsonicRestClient,
            final ErrorHandler errorHandler, final Messages messages) {
        this.subsonicRestClient = subsonicRestClient;
        this.errorHandler = errorHandler;
        this.messages = messages;
    }

    private StreamRequestBuilder stream(final SubsonicCredentials configuration) {
        return new StreamRequestBuilder(configuration).withFormat("mp3").withMaxBitRate(0);
    }

    @Override
    public String getIntentName() {
        return INTENTNAME;
    }

    @Override
    public SpeechletResponse onIntent(final RequestContext<IntentRequest> context) {

        final SubsonicCredentials subsonicCredentials = context.requireSubsonicCredentials();

        final GetRandomSongsRequestBuilder randomSongsRequest = new GetRandomSongsRequestBuilder(subsonicCredentials)
                .withSize(50);
        final FlatteningSubsonicResponseConverter<Songs, List<Child>> randomSongsConverter = ResponseConverters
                .getRandomSongs();

        final List<Child> songs = subsonicRestClient.executeAndFlatten(randomSongsRequest, randomSongsConverter);

        if (songs.isEmpty()) {
            return errorHandler.onError(context, MESSAGEKEY_NO_SONGS);
        }

        // Create new playlist
        final Playlist playlist = context.getDeviceSession().getPlaylist();
        playlist.clear();

        for (final Child song : songs) {
            final String url = stream(subsonicCredentials).withId(song.getId()).getUrl();
            playlist.add(url);
        }

        // Create response
        final Child firstSong = songs.get(0);

        final PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setId("RandomSongsIntro");
        outputSpeech.setText(messages.getMessage(//
                MESSAGEKEY_START, //
                context.getLocale(), //
                firstSong.getTitle(), //
                firstSong.getArtist() //
        ));

        final SimpleCard card = new SimpleCard();
        card.setTitle(messages.getMessage(MESSAGEKEY_CARD_TITLE, context.getLocale()));
        final List<String> songTitles = songs.stream() //
                .map(s -> String.format("%s: %s (%s)", s.getArtist(), s.getTitle(), s.getYear())) //
                .collect(Collectors.toList());

        card.setContent(StringUtils.join(songTitles, "\n"));

        final SpeechletResponse response = SpeechletResponse.newTellResponse(outputSpeech, card);
        return SpeechletResponseUtils.addPlayDirective(response, playlist.first(), PlayBehavior.REPLACE_ALL);
    }

}
