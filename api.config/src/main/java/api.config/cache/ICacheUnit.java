package api.config.cache;

import java.time.Duration;

public interface ICacheUnit
{
    /**
     * 获取缓存
     * @param key
     * @retrun T
     */
    <T> T get(String key);

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param timeSpan 超时时间
     * @return 是否设置成功
     */
    void set(String key, Object value, Duration timeSpan);

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @return 是否设置成功
     */
    void set(String key, Object value);

    /**
     * 清除缓存
     * @param key
     * @return 是否删除成功
     */
    boolean clear(String key);
}
