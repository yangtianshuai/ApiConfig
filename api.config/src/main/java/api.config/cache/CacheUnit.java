package api.config.cache;

public class CacheUnit {
    public static ICacheUnit Current;

    public CacheUnit(ICacheUnit cacheUnit)
    {
        Current = cacheUnit;
    }
}
