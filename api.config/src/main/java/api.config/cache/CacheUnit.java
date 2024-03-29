package api.config.cache;

import java.time.Duration;

public class CacheUnit {
    public static ICacheUnit Current;

    protected static Duration out_time = Duration.ofSeconds(60*30);

    public CacheUnit(ICacheUnit cacheUnit)
    {
        Current = cacheUnit;
    }

    public void setOutTime(int second){
        out_time = Duration.ofSeconds(second);
    }
}
