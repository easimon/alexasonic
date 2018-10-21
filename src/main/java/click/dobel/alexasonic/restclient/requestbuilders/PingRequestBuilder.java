package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.ResponseStatus;

public class PingRequestBuilder extends AbstractSubsonicRequestBuilder<PingRequestBuilder, ResponseStatus> {

  PingRequestBuilder() {
    super("ping");
  }
}
