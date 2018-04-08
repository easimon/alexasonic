package click.dobel.alexasonic.configuration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import click.dobel.alexasonic.domain.session.DeviceSession;
import io.jsondb.JsonDBTemplate;
import io.jsondb.crypto.CryptoUtil;
import io.jsondb.crypto.DefaultAESCBCCipher;
import io.jsondb.crypto.ICipher;

@Configuration
public class JsonDBConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDBConfiguration.class);

    private static final String JSONDB_BASE_SCAN_PACKAGE = "click.dobel.alexasonic.domain";
    private static final List<Class<?>> ENTITY_CLASSES = Arrays.asList(DeviceSession.class);

    @Value("${application.db.path}")
    private String dbFilesLocationString;

    @Value("${application.db.persistence-mode:persistent}")
    private String persistenceMode;

    private void ensureDbFilesLocation() throws IOException {
        if (StringUtils.isEmpty(dbFilesLocationString)) {
            throw new IllegalArgumentException("JsonDB files location is empty.");
        }
    }

    /*
     * TODO: implement secure cipher with configurable key. For now this is only a
     * protection against accidentally reading passwords from DB files when
     * debugging.
     */
    @Bean
    public ICipher encryptionCipher() throws GeneralSecurityException, UnsupportedEncodingException {
        final String secretKey = CryptoUtil.generate128BitKey("TotalGeheim", "MitEtwasSalz");
        return new DefaultAESCBCCipher(secretKey);
    }

    @Bean
    public JsonDBTemplate jsonDbTemplate(final ICipher encryptionCipher) throws IOException, GeneralSecurityException {
        ensureDbFilesLocation();

        final JsonDBTemplate template = new JsonDBTemplate(dbFilesLocationString, JSONDB_BASE_SCAN_PACKAGE,
                encryptionCipher);

        ENTITY_CLASSES.stream() //
                .filter(c -> !template.collectionExists(c)) //
                .forEach(c -> template.createCollection(c));

        return template;
    }

    private boolean isPersistenceModeTemporary() {
        return "temporary".equals(persistenceMode.trim());
    }

    @Bean
    public ApplicationListener<ContextClosedEvent> temporaryDatabaseDeleterListener(
            final JsonDBTemplate jsonDbTemplate) {
        return e -> {
            if (isPersistenceModeTemporary()) {
                LOGGER.info("Clearing JsonDB database.");
                jsonDbTemplate.getCollectionNames().forEach(name -> jsonDbTemplate.dropCollection(name));

            } else {
                LOGGER.info("Keeping JsonDB database.");
            }
        };
    }

}
