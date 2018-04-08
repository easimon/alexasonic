package click.dobel.alexasonic.restclient.responseconverters;

import org.subsonic.restapi.Response;

public class FlatteningSubsonicResponseConverter<T, F>
        implements SubsonicResponseConverter<T>, SubsonicResponseFlattener<T, F> {

    private final SubsonicResponseConverter<T> converter;

    private final SubsonicResponseFlattener<T, F> flattener;

    private FlatteningSubsonicResponseConverter(final SubsonicResponseConverter<T> converter,
            final SubsonicResponseFlattener<T, F> flattener) {
        this.converter = converter;
        this.flattener = flattener;
    }

    public static <T, F> FlatteningSubsonicResponseConverter<T, F> of(final SubsonicResponseConverter<T> converter,
            final SubsonicResponseFlattener<T, F> flattener) {
        return new FlatteningSubsonicResponseConverter<>(converter, flattener);
    }

    @Override
    public T convert(final Response response) {
        return converter.convert(response);
    }

    @Override
    public F flatten(final T convertedResponse) {
        return flattener.flatten(convertedResponse);
    }

    public final F convertAndFlatten(final Response response) {
        return flattener.flatten(converter.convert(response));
    }

}
