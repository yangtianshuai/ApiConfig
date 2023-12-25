package api.config.utility;

public class StringUtil {
    public static Boolean isNullOrEmpty(String value){
        return value == null || value.length() == 0;
    }
}
