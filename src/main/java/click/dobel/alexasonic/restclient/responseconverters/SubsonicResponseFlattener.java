package click.dobel.alexasonic.restclient.responseconverters;

@FunctionalInterface
public interface SubsonicResponseFlattener<T, F> {

    F flatten(final T convertedResponse);

}
