package repository.utils;

import java.util.Objects;

public class StringQueryUtils {

    public static boolean isParamEnd(char c) {

        return Objects.equals(c, ' ') || Objects.equals(c, ',') || Objects.equals(c, '(') || Objects.equals(c, ')');

    }

}
