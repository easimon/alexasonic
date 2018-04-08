package click.dobel.alexasonic.restclient.requestbuilders;

import java.net.URI;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public abstract class AbstractSubsonicRequestBuilder<B extends AbstractSubsonicRequestBuilder<B, T>, T>
        implements RequestParameters {

    private static final int SALT_LENGTH = 12;

    public static final String REST_CLIENT_VERSION = "1.15.0";
    public static final String REST_CLIENT_NAME = "AlexaSonic";
    private static final String REST_CLIENT_REQUEST_FORMAT = "json";

    private static final String REST_ENDPOINT_URL_PATH = "rest/";

    private final SubsonicCredentials subsonicCredentials;

    private final UriComponentsBuilder uriComponentsBuilder;

    private static String createRestBaseUrl(final String serverUrl) {
        return serverUrl + (serverUrl.endsWith("/") ? "" : "/") + REST_ENDPOINT_URL_PATH;
    }

    protected AbstractSubsonicRequestBuilder(final SubsonicCredentials subsonicCredentials,
            final String restResourceName) {
        this.subsonicCredentials = subsonicCredentials;
        this.uriComponentsBuilder = UriComponentsBuilder //
                .fromHttpUrl(createRestBaseUrl(subsonicCredentials.getUrl()) + restResourceName);
    }

    private String createSalt() {
        return RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
    }

    private String createToken(final String password, final String salt) {
        final String passwordAndSalt = password + salt;
        return DigestUtils.md5Hex(passwordAndSalt);
    }

    private void addGeneralParameters() {
        final String salt = createSalt();

        this.uriComponentsBuilder //
                .queryParam("u", subsonicCredentials.getUsername())
                .queryParam("t", createToken(subsonicCredentials.getPassword(), salt)) //
                .queryParam("s", salt) //
                .queryParam("v", REST_CLIENT_VERSION) //
                .queryParam("c", REST_CLIENT_NAME) //
                .queryParam("f", REST_CLIENT_REQUEST_FORMAT);
    }

    @SuppressWarnings("unchecked")
    protected B with(final String name, final Object value) {
        if (value != null) {
            uriComponentsBuilder.queryParam(name, String.valueOf(value));
        }
        return (B) this;
    }

    public URI getUri() {
        addGeneralParameters();
        return uriComponentsBuilder.build().encode().toUri();
    }

    public String getUrl() {
        return getUri().toString();
    }

}
