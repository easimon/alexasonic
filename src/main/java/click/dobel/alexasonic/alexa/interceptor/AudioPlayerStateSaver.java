package click.dobel.alexasonic.alexa.interceptor;

import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.repository.DeviceSessionRepository;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AudioPlayerStateSaver implements RequestInterceptor {

  private final DeviceSessionRepository deviceSessionRepository;

  @Autowired
  public AudioPlayerStateSaver(final DeviceSessionRepository deviceSessionRepository) {
    this.deviceSessionRepository = deviceSessionRepository;
  }

  @Override
  public void process(final HandlerInput input) {
    final DeviceSession session = deviceSessionRepository.getDeviceSession(input);

    SpeechletRequestUtil.getAudioPlayerState(input.getRequestEnvelope()).ifPresent(state -> {
      session.setLastAudioPlayerToken(state.getToken());
      session.setLastAudioPlayerOffsetInMilliseconds(state.getOffsetInMilliseconds());
      deviceSessionRepository.saveDeviceSession(session, input);
    });
  }
}
