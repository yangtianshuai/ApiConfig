package api.config.cache;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class MemoryCacheUnit implements ICacheUnit {


    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private static Map<String, CacheItem> _cache;

    public MemoryCacheUnit() {
        _cache = new ConcurrentHashMap<>();

        scheduler.scheduleAtFixedRate(() -> {
            _cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
        }, 0, CacheUnit.out_time.toMillis(), TimeUnit.MILLISECONDS);
    }

    public <T> T get(String key) {
        CacheItem item = _cache.get(key);
        if (item != null && System.currentTimeMillis() < item.expire_time) {
            return (T)item.value;
        }
        return null;
    }

    public void set(String key, Object value, Duration timeSpan) {
        long expire_time = System.currentTimeMillis() + timeSpan.toMillis();
        CacheItem item = new CacheItem(value, expire_time);
        _cache.put(key, item);
    }

    public void set(String key, Object value) {
        long expire_time = System.currentTimeMillis() + CacheUnit.out_time.toMillis();
        CacheItem item = new CacheItem(value, expire_time);
        _cache.put(key, item);
    }

    public Boolean clear(String key) {
        if(_cache.containsKey(key)){
            _cache.remove(key);
            return true;
        }
        return false;
    }
}
