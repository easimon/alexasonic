package click.dobel.alexasonic.i18n;

import java.util.Locale;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

@Component
public class Messages {

    private final MessageSource messageSource;

    @Autowired
    public Messages(final MessageSource messageSource) {
        Objects.requireNonNull(messageSource, "MessageSource may not be null.");
        this.messageSource = messageSource;
    }

    public String getMessage(final String code, final Locale locale, final Object... args) {
        return messageSource.getMessage(code, args, locale);
    }

    public String getMessage(final String code, final String defaultCode, final Locale locale, final Object... args) {
        try {
            return messageSource.getMessage(code, args, locale);
        } catch (final NoSuchMessageException exception) {
            return messageSource.getMessage(defaultCode, args, locale);
        }
    }

}
