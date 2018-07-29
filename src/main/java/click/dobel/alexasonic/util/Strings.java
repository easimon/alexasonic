package click.dobel.alexasonic.util;

import com.google.common.base.Preconditions;

import java.util.Iterator;

public final class Strings {

    private Strings() {
    }

    public static final String EMPTY = "";

    public static boolean isNull(final CharSequence string) {
        return string == null;
    }

    public static boolean isEmpty(final String string) {
        return isNull(string) || EMPTY.equals(string);
    }

    public static String left(final String string, final int len) {
        Preconditions.checkArgument(len >= 0, "Length must not be negative.");
        if (isNull(string)) {
            return null;
        }
        final int stringLength = string.length();
        if (stringLength <= len) {
            return string;
        }
        return string.substring(0, len);
    }

    public static String right(final String string, final int len) {
        Preconditions.checkArgument(len >= 0, "Length must not be negative.");
        if (isNull(string)) {
            return null;
        }
        final int stringLength = string.length();
        if (stringLength <= len) {
            return string;
        }
        return string.substring(stringLength - len);
    }

    public static <S extends CharSequence> String join(final Iterable<S> items, final CharSequence separator) {

        final Iterator<S> iter = items != null ? items.iterator() : null;

        if (iter == null || !iter.hasNext()) {
            return EMPTY;
        }

        final StringBuilder builder = new StringBuilder(iter.next());

        while (iter.hasNext()) {
            builder
                .append(separator)
                .append(iter.next());
        }

        return builder.toString();
    }
}
