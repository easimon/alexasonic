package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.License;

import click.dobel.alexasonic.configuration.SubsonicCredentials;

public class GetLicenseRequestBuilder extends AbstractSubsonicRequestBuilder<GetLicenseRequestBuilder, License> {

    public GetLicenseRequestBuilder(final SubsonicCredentials configuration) {
        super(configuration, "getLicense");
    }

}
