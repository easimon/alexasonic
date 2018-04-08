package click.dobel.alexasonic.restclient;

import org.subsonic.restapi.Error;
import org.subsonic.restapi.ResponseStatus;

public class SubsonicRestServiceFailure extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final ResponseStatus status;
    private final Error error;

    public SubsonicRestServiceFailure(ResponseStatus status, Error error) {
        this.status = status;
        this.error = error;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public Error getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return getStatus().name() + ": [" + getError().getCode() + "] " + getError().getMessage();
    }

}
