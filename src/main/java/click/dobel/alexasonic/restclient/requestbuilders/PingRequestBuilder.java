package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.ResponseStatus;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public class PingRequestBuilder extends AbstractSubsonicRequestBuilder<PingRequestBuilder, ResponseStatus> {

    public PingRequestBuilder(final SubsonicCredentials configuration) {
        super(configuration, "ping");
    }

}
