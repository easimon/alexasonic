package click.dobel.alexasonic.restclient.responseconverters;

import org.subsonic.restapi.Response;

@FunctionalInterface
public interface SubsonicResponseConverter<T> {

  T convert(Response response);
}
