package click.dobel.alexasonic.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import click.dobel.alexasonic.domain.session.DeviceSession;
import io.jsondb.JsonDBTemplate;

@Component
public class SessionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepository.class);

    private final JsonDBTemplate jsonDbTemplate;

    @Autowired
    public SessionRepository(final JsonDBTemplate jsonDBTemplate) {
        this.jsonDbTemplate = jsonDBTemplate;
    }

    public DeviceSession getDeviceSession(final String deviceId) {
        final DeviceSession session = jsonDbTemplate.findById(deviceId, DeviceSession.class);
        if (session != null) {
            LOGGER.debug("DeviceSession found.");
            return session;
        } else {
            LOGGER.debug("No DeviceSession found, creating new one.");
            return new DeviceSession(deviceId);
        }
    }

    public void saveDeviceSession(final DeviceSession deviceSession) {
        if (deviceSession != null)
            jsonDbTemplate.upsert(deviceSession);
    }

}
