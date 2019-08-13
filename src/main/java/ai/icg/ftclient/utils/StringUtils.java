package ai.icg.ftclient.utils;

public class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }
}
