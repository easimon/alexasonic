package click.dobel.alexasonic.restclient.requestbuilders;

import org.subsonic.restapi.License;

public class GetLicenseRequestBuilder extends AbstractSubsonicRequestBuilder<GetLicenseRequestBuilder, License> {

    GetLicenseRequestBuilder() {
        super("getLicense");
    }
}
