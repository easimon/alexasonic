package click.dobel.alexasonic.restclient.requestbuilders;

import click.dobel.alexasonic.configuration.SubsonicCredentials;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractSubsonicRequestBuilder<B extends AbstractSubsonicRequestBuilder<B, T>, T> {

  private static final int SALT_LENGTH = 12;

  public static final String REST_CLIENT_VERSION = "1.15.0";
  public static final String REST_CLIENT_NAME = "AlexaSonic";
  private static final String REST_CLIENT_REQUEST_FORMAT = "json";

  private static final String REST_ENDPOINT_URL_PATH = "rest/";

  private final String restResourceName;

  private final Map<String, Object> requestParams = new HashMap<>();

  private static String createRestBaseUrl(final String serverUrl) {
    return serverUrl + (serverUrl.endsWith("/") ? "" : "/") + REST_ENDPOINT_URL_PATH;
  }

  protected AbstractSubsonicRequestBuilder(final String restResourceName) {
    this.restResourceName = restResourceName;
  }

  private String createSalt() {
    return RandomStringUtils.randomAlphanumeric(SALT_LENGTH);
  }

  private String createToken(final String password, final String salt) {
    final String passwordAndSalt = password + salt;
    return DigestUtils.md5Hex(passwordAndSalt);
  }

  private Map<String, Object> createGeneralParameters(final SubsonicCredentials credentials) {
    final Map<String, Object> result = new HashMap<>();

    final String salt = createSalt();
    result.put("u", credentials.getUsername());
    result.put("t", createToken(credentials.getPassword(), salt));
    result.put("s", salt);
    result.put("v", REST_CLIENT_VERSION);
    result.put("c", REST_CLIENT_NAME);
    result.put("f", REST_CLIENT_REQUEST_FORMAT);
    return result;
  }

  @SuppressWarnings("unchecked")
  protected B with(final String name, final Object value) {
    if (value != null) {
      requestParams.put(name, value);
    }
    return (B) this;
  }

  public URI getUri(final SubsonicCredentials credentials) {
    final UriComponentsBuilder builder = UriComponentsBuilder
      .fromHttpUrl(createRestBaseUrl(credentials.getUrl()) + restResourceName);

    createGeneralParameters(credentials).forEach(builder::queryParam);
    requestParams.forEach(builder::queryParam);

    return builder.build().encode().toUri();
  }

  public String getUrl(final SubsonicCredentials credentials) {
    return getUri(credentials).toString();
  }
}
