package api.config.cache;

public class CacheItem {

    protected Object value;
    protected long expire_time;

    public CacheItem(Object value, long expire_time) {
        this.value = value;
        this.expire_time = expire_time;
    }

    protected  boolean isExpired() {
        return System.currentTimeMillis() > System.currentTimeMillis() + expire_time;
    }
}