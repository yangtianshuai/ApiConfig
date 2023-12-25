package api.config.setting;

import java.lang.reflect.Type;

public class AppSetting {

    public static String getSetting(String key){
        return "";
    }

    public static <T> T getSetting(String key, Type clazz){
        return null;
    }
}