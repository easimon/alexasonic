package click.dobel.alexasonic.exception;

public class AlexaSonicException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorMessageKey;
    private final Object[] errorMessageArgs;

    public AlexaSonicException(final String errorMessageKey, final Object... errorMessageArgs) {
        this.errorMessageKey = errorMessageKey;
        this.errorMessageArgs = errorMessageArgs;
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    public Object[] getErrorMessageArgs() {
        return errorMessageArgs;
    }

    @Override
    public String getMessage() {
        return getErrorMessageKey();
    }

}