package click.dobel.alexasonic.exception;

public class AlexaSonicException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorMessageKey;
    private final Object[] errorMessageArgs;

    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public AlexaSonicException(final String errorMessageKey, final Object... errorMessageArgs) {
        super();
        this.errorMessageKey = errorMessageKey;
        this.errorMessageArgs = errorMessageArgs;
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public Object[] getErrorMessageArgs() {
        return errorMessageArgs;
    }

    @Override
    public String getMessage() {
        return getErrorMessageKey();
    }

}