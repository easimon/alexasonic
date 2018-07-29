package click.dobel.alexasonic.repository;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractAttributesRepository<T> {

    private final String attributeKey;
    private final Function<HandlerInput, Map<String, Object>> attributesSupplier;

    protected AbstractAttributesRepository(
        final String attributeKey,
        final Function<HandlerInput, Map<String, Object>> attributesSupplier
    ) {
        this.attributeKey = attributeKey;
        this.attributesSupplier = attributesSupplier;
    }

    protected static Function<HandlerInput, Map<String, Object>> PERSISTENT = input ->
        input
            .getAttributesManager()
            .getPersistentAttributes();

    protected static Function<HandlerInput, Map<String, Object>> SESSION = input ->
        input
            .getAttributesManager()
            .getSessionAttributes();

    protected static Function<HandlerInput, Map<String, Object>> REQUEST = input ->
        input
            .getAttributesManager()
            .getRequestAttributes();

    private Map<String, Object> getAttributes(final HandlerInput input) {
        return attributesSupplier.apply(input);
    }

    @SuppressWarnings("unchecked")
    protected T get(final HandlerInput input) {
        return (T) getAttributes(input).get(attributeKey);
    }

    @SuppressWarnings("unchecked")
    protected T put(final T value, final HandlerInput input) {
        final T oldValue = (T) getAttributes(input).put(attributeKey, value);
        if (PERSISTENT.equals(attributesSupplier) && !Objects.equals(value, oldValue)) {
            input.getAttributesManager().getRequestAttributes().put("persistentAttributesDirty", Boolean.TRUE);
            input.getAttributesManager().savePersistentAttributes();
        }
        return oldValue;
    }

    protected T getOrDefault(final HandlerInput input, final Function<HandlerInput, T> defaultValueSupplier) {
        final T result = get(input);
        if (result == null) {
            return defaultValueSupplier.apply(input);
        }
        return result;
    }

}
