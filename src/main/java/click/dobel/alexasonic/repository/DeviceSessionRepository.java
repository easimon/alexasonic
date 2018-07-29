package click.dobel.alexasonic.repository;

import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.speechlet.SpeechletRequestUtil;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import org.springframework.stereotype.Component;

@Component
public class DeviceSessionRepository extends AbstractAttributesRepository<DeviceSession> {

    private static final String ATTRIBUTE_NAME = "deviceSession";

    public DeviceSessionRepository() {
        super(ATTRIBUTE_NAME, AbstractAttributesRepository.PERSISTENT);
    }

    public DeviceSession getDeviceSession(final HandlerInput input) {
        return getOrDefault(input, i ->
            new DeviceSession(SpeechletRequestUtil.getDeviceId(i.getRequestEnvelope()))
        );
    }

    public DeviceSession saveDeviceSession(final DeviceSession deviceSession, final HandlerInput input) {
        return put(deviceSession, input);
    }
}
