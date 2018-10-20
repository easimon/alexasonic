package click.dobel.alexasonic.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class AlexaSonicException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorMessageKey;
    private final List<Object> errorMessageArgs;

    public AlexaSonicException(final String errorMessageKey, final Object... errorMessageArgs) {
        super();
        this.errorMessageKey = errorMessageKey;
        this.errorMessageArgs = Arrays.asList(errorMessageArgs);
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

    public Object[] getErrorMessageArgs() {
        return errorMessageArgs.toArray();
    }

    @Override
    public String getMessage() {
        if (errorMessageArgs.isEmpty()) {
            return getErrorMessageKey();
        } else {
            final String args = " [" + StringUtils.join(errorMessageArgs, ',') + "]";
            return getErrorMessageKey() + args;
        }
    }
}