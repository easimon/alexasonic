package click.dobel.alexasonic.exception;

import java.util.Optional;

public final class Optionals {

    private Optionals() {
    }

    public static <T> T require(final Optional<T> opt, final String errorMessageKey, final Object... errorMessageArgs) {
        return opt.orElseThrow(() -> new AlexaSonicException(errorMessageKey, errorMessageArgs));
    }
}
