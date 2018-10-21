package click.dobel.alexasonic.restclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.subsonic.restapi.Response;

import javax.validation.constraints.NotNull;

public class SubsonicResponseWrapper {

  @JsonProperty("subsonic-response")
  private Response response;

  @NotNull
  public Response getResponse() {
    return response;
  }

  public void setResponse(final Response response) {
    this.response = response;
  }
}
