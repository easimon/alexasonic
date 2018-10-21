package click.dobel.alexasonic.alexa.handlers.custom;

import click.dobel.alexasonic.alexa.handlers.AbstractDeviceSessionAwareRequestHandler;
import click.dobel.alexasonic.configuration.SubsonicCredentials;
import click.dobel.alexasonic.domain.playlist.Playlist;
import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.exception.AlexaSonicException;
import click.dobel.alexasonic.i18n.Messages;
import click.dobel.alexasonic.repository.DeviceSessionRepository;
import click.dobel.alexasonic.repository.SubsonicCredentialsRepository;
import click.dobel.alexasonic.restclient.SubsonicRestClient;
import click.dobel.alexasonic.restclient.requestbuilders.RequestBuilders;
import click.dobel.alexasonic.restclient.requestbuilders.StreamRequestBuilder;
import click.dobel.alexasonic.restclient.responseconverters.ResponseConverters;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.request.Predicates;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.subsonic.restapi.Child;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayRandomSongsIntentHandler extends AbstractDeviceSessionAwareRequestHandler {

  public static final String INTENTNAME = "PlayRandomSongs";

  private static final String MESSAGEKEY_START = INTENTNAME + ".Start";
  private static final String MESSAGEKEY_NO_SONGS = INTENTNAME + ".NoSongs";
  private static final String MESSAGEKEY_CARD_TITLE = INTENTNAME + ".CardTitle";

  private final SubsonicCredentialsRepository subsonicCredentialsRepository;
  private final SubsonicRestClient subsonicRestClient;
  private final Messages messages;

  @Autowired
  public PlayRandomSongsIntentHandler(
    final DeviceSessionRepository deviceSessionRepository,
    final SubsonicCredentialsRepository subsonicCredentialsRepository,
    final SubsonicRestClient subsonicRestClient,
    final Messages messages
  ) {
    super(deviceSessionRepository);
    this.subsonicCredentialsRepository = subsonicCredentialsRepository;
    this.subsonicRestClient = subsonicRestClient;
    this.messages = messages;
  }

  @Override
  public boolean canHandle(final HandlerInput input) {
    return input.matches(Predicates.intentName(INTENTNAME));
  }

  @Override
  protected Optional<Response> handle(final HandlerInput input, final DeviceSession deviceSession) {
    final String userId = SpeechletRequestUtil.getUserId(input.getRequestEnvelope());
    final Locale locale = SpeechletRequestUtil.getLocale(input);

    final SubsonicCredentials subsonicCredentials = subsonicCredentialsRepository.getCredentialsForUser(userId);

    final List<Child> songs = subsonicRestClient.executeAndFlatten(
      RequestBuilders.getRandomSongs().withSize(50),
      ResponseConverters.RANDOM_SONGS,
      subsonicCredentials
    );

    if (songs.isEmpty()) {
      throw new AlexaSonicException(MESSAGEKEY_NO_SONGS);
    }

    // Create new playlist
    final Playlist playlist = deviceSession.getPlaylist();
    playlist.clear();

    for (final Child song : songs) {
      final String url = stream().withId(song.getId()).getUrl(subsonicCredentials);
      playlist.add(url);
    }

    // Create response
    final Child firstSong = songs.get(0);

    return input.getResponseBuilder()
      .withSpeech(messages.getMessage(
        MESSAGEKEY_START,
        locale,
        firstSong.getTitle(),
        firstSong.getArtist()))
      .withSimpleCard(
        messages.getMessage(MESSAGEKEY_CARD_TITLE, locale),
        StringUtils.join(
          songs.stream()
            .map(song -> String.format(
              "%s: %s (%s)",
              song.getArtist(),
              song.getTitle(),
              song.getYear()
              )
            )
            .collect(Collectors.toList()),
          "\n")
      )
      .addAudioPlayerPlayDirective(
        PlayBehavior.REPLACE_ALL,
        null,
        null, playlist.first(), playlist.first())
      .build();
  }

  private StreamRequestBuilder stream() {
    return RequestBuilders.stream().withFormat("mp3").withMaxBitRate(0);
  }
}
