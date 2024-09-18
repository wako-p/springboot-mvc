package jp.wako.demo.springbootmvc.presentation.shared.helper;

public final class LongHelper {

    public static boolean unconvertible(final String value) {
        return !isConvertible(value);
    }

    public static boolean isConvertible(final String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
