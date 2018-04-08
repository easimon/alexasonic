package click.dobel.alexasonic.restclient;

import org.subsonic.restapi.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubsonicResponseWrapper {

    @JsonProperty("subsonic-response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
