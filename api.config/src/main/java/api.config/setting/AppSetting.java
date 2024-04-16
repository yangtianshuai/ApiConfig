package api.config.setting;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

public class AppSetting {
    private static ConcurrentHashMap<String,Object> settings =  new ConcurrentHashMap<>();

    public static String getSetting(String key){
        if(settings.containsKey(key)){
            return settings.get(key).toString();
        }
        return "";
    }

    public static <T> T getSetting(String key, Type clazz){
        if(settings.containsKey(key)){
            return (T)settings.get(key);
        }
        return null;
    }
}