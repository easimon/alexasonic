package click.dobel.alexasonic.alexa.handlers;

import click.dobel.alexasonic.domain.session.DeviceSession;
import click.dobel.alexasonic.repository.DeviceSessionRepository;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

public abstract class AbstractDeviceSessionAwareRequestHandler implements RequestHandler {

    private final DeviceSessionRepository deviceSessionRepository;

    protected AbstractDeviceSessionAwareRequestHandler(final DeviceSessionRepository deviceSessionRepository) {
        this.deviceSessionRepository = deviceSessionRepository;
    }

    public abstract Optional<Response> handle(HandlerInput input, DeviceSession deviceSession);

    @Override
    public final Optional<Response> handle(final HandlerInput input) {
        final DeviceSession deviceSession = deviceSessionRepository.getDeviceSession(input);
        try {
            return handle(input, deviceSession);
        } finally {
            deviceSessionRepository.saveDeviceSession(deviceSession, input);
        }
    }
}
